# Handover: sticlă de Prosecco în coșul Sezamo — exclusiv Playwright CLI

Instrucțiuni pas-cu-pas pentru un agent (ex. GitHub Copilot) care trebuie să repete
task-ul: **adaugă o sticlă de Prosecco în coșul de pe sezamo.ro folosind DOAR
Playwright CLI** — fără MCP Playwright, fără extensii de Chrome, fără DevTools.

## Ce s-a făcut deja (starea curentă)

- Sesiune Playwright CLI `default` deschisă **headed + profil persistent**, browser lăsat deschis.
- În coș: **Mionetto IL Prosecco, vin frizzante prosecco, 0,75 l** — 45,89 lei
  (-10% până la 31/7) + 0,50 lei garanție SGR = **46,39 lei total**, drawer-ul „Coș" lăsat deschis pe ecran.
- Coșul e **anonim** (fără login) — Sezamo ține coșul pe cookie-uri; login-ul trebuie abia la checkout.
- Backup de stare (cookies): `sezamo-session.json` (salvat cu `state-save`, în scratchpad-ul sesiunii Claude).

## Prerechizite

- `@playwright/test` ≥ 1.62 instalat local (`npm i -D @playwright/test`) — include CLI-ul agentic.
  În acest repo e deja instalat: binarul e `node_modules/.bin/playwright`.
- Browserele Playwright instalate (`npx playwright install chromium` dacă e mediu proaspăt).

## Concepte cheie Playwright CLI

- Invocare: `node_modules/.bin/playwright cli <comandă>` (echivalent `playwright-cli`).
- **Stateful prin daemon**: browserul rămâne deschis între comenzi până rulezi `close`. NU rula `close` la final — cerința e să rămână coșul deschis pentru utilizator.
- Elementele se țintesc prin **ref-uri** (`e123`) obținute din `snapshot` sau `find`. Ref-urile se schimbă când se schimbă pagina → **întotdeauna `find` proaspăt înainte de `click`**, nu refolosi ref-uri vechi (nici pe cele din exemplele de mai jos).
- Dacă există deja o sesiune `default` activă (ex. cea lăsată de Claude), folosește o sesiune separată: prefixează fiecare comandă cu `-s=copilot`.

## Pașii de urmat

```bash
PW=node_modules/.bin/playwright

# 1. Deschide browser vizibil cu profil persistent, direct pe site
$PW cli open --headed --persistent https://www.sezamo.ro

# 2. Prima vizită pe profil proaspăt: dialog de cookies (Usercentrics)
#    Găsește și apasă butonul „Acceptați toate modulele cookie"
$PW cli find --regex "/accepta/i"        # notează ref-ul butonului
$PW cli click <refAcceptCookies>

# 3. Caută produsul: combobox „Caută un produs sau o categorie"
$PW cli find --regex "/caută un produs/i"
$PW cli fill <refSearch> "prosecco" --submit
# → navighează la /cautare?q=prosecco&companyId=1

# 4. (opțional) Listează toate produsele din rezultate, cu id + nume + preț:
$PW cli --raw eval "JSON.stringify([...document.querySelectorAll('[data-test^=productCard-]')].map(c => ({id: c.getAttribute('data-test'), name: c.querySelector('a')?.getAttribute('aria-label')})))"

# 5. Adaugă produsul ales: butonul „+" are aria-label „Adăugați o bucată. <Nume produs>"
$PW cli find "Adăugați o bucată. Mionetto IL Prosecco"
$PW cli click <refPlus>

# 6. Verifică — headerul coșului trebuie să arate 1 articol:
$PW cli find --regex "/în coșul tău/i"
# aștept: button "Ai 1 articole în valoare de 46,39 lei în coșul tău de cumpărături."

# 7. Lasă coșul la vedere: click pe butonul de coș din header → se deschide drawer „Coș"
$PW cli click <refCartButton>

# 8. (opțional) Backup stare + screenshot dovadă
$PW cli state-save sezamo-session.json
$PW cli screenshot --filename=sezamo-cos-prosecco.png

# 9. STOP. NU rula `close` — browserul rămâne deschis cu coșul plin.
```

## Repere de produse Prosecco (id-uri văzute pe 2026-07-28)

| Produs (0,75 l) | id | Preț |
|---|---|---|
| **Mionetto IL Prosecco frizzante** (alegerea făcută) | 4955 | 45,89 lei (-10% până la 31/7) |
| Pasqua Prosecco Brut R&J | 39931 | 50,99 lei |
| Zonin Prosecco D.O.C. brut | 21009 | 55,99 lei |
| Mionetto Orange DOC Treviso (max 3 buc) | 4947 | 55,99 lei |
| Brilla Extra Dry Prosecco | 31706 | 67,89 lei |
| Cipriani Prosecco brut | 46931 | 99,99 lei |

Card-ul de produs: `data-test="productCard-AVAILABLE-<id>"`; butonul plus:
`data-test="productCard-header-counterButton-plus-button"`. URL direct produs: `/<id>-<slug>` (ex. `/4955-mionetto-il-prosecco-vin-frizzante-prosecco`).

## Capcane

- **Ref-urile expiră** la fiecare schimbare de pagină — re-rulează `find` înainte de fiecare `click`.
- Peste dialogul de login poate apărea un popup promoțional; nu e cazul aici (nu facem login).
- Coșul adaugă automat **garanția SGR 0,50 lei/sticlă** — totalul va fi cu 0,50 mai mare decât prețul.
- **NU plasa comanda** — task-ul se oprește la coș. Checkout doar cu confirmare explicită de la Victor.
- Snapshot-urile/screenshot-urile se scriu în `./.playwright-cli/` relativ la cwd — rulează dintr-un folder de lucru temporar dacă nu le vrei în repo.
