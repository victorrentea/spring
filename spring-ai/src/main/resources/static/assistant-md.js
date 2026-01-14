// Minimal markdown -> HTML renderer extracted from index.html
// Exposes a global `renderMarkdown(md)` function.
(function (global) {
  'use strict';

  function escapeHtml(s) {
    return String(s)
      .replace(/&/g, '&amp;')
      .replace(/</g, '&lt;')
      .replace(/>/g, '&gt;')
      .replace(/"/g, '&quot;');
  }

  function renderMarkdown(md) {
    if (!md) return '';
    // Handle code fences first
    var out = md;
    out = out.replace(/```([\s\S]*?)```/g, function (_, code) {
      return '<pre><code>' + escapeHtml(code) + '</code></pre>';
    });

    // Escape remaining HTML
    out = escapeHtml(out);

    // Restore the already rendered code blocks (they were escaped) by replacing escaped form
    out = out.replace(/&lt;pre&gt;&lt;code&gt;([\s\S]*?)&lt;\/code&gt;&lt;\/pre&gt;/g, function (_, inner) {
      return '<pre><code>' + inner + '</code></pre>';
    });

    // headings
    out = out.replace(/^###### (.*$)/gim, '<h6>$1</h6>');
    out = out.replace(/^##### (.*$)/gim, '<h5>$1</h5>');
    out = out.replace(/^#### (.*$)/gim, '<h4>$1</h4>');
    out = out.replace(/^### (.*$)/gim, '<h3>$1</h3>');
    out = out.replace(/^## (.*$)/gim, '<h2>$1</h2>');
    out = out.replace(/^# (.*$)/gim, '<h1>$1</h1>');

    // links [text](url)
    out = out.replace(/\[([^\]]+)]\(([^)]+)\)/g, function (_, text, url) {
      return '<a href="' + url + '" target="_blank" rel="noopener">' + text + '</a>';
    });

    // bold and italic
    out = out.replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>');
    out = out.replace(/\*(.*?)\*/g, '<em>$1</em>');

    // inline code
    out = out.replace(/`([^`]+)`/g, '<code>$1</code>');

    // lists: transform lines starting with - or * into <ul>
    out = out.replace(/(^|\n)([-*] .+(?:\n[-*] .+)*)/g, function (_, lead, items) {
      var lis = items.trim().split(/\n/).map(function (l) { return '<li>' + l.replace(/^[-*]\s+/, '') + '</li>'; }).join('');
      return lead + '<ul>' + lis + '</ul>';
    });

    // paragraphs: split by two newlines
    out = out.replace(/\n{2,}/g, '</p><p>');
    // single newlines to <br>
    out = out.replace(/\n/g, '<br>');

    // wrap in <p> if not starting with block element
    if (!out.match(/^\s*<(?:h[1-6]|ul|pre|blockquote|p)/)) {
      out = '<p>' + out + '</p>';
    }
    return out;
  }

  // Export to global
  global.renderMarkdown = renderMarkdown;
  global.escapeHtml = escapeHtml;

})(window);

