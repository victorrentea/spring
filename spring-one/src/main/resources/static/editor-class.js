function _(selection) {
    return document.querySelectorAll(selection);
}

function _$(events, eles, callback) {
    for(let e of eles) {
        for (let ev of events.split(' ')) {
            e.addEventListener(ev, callback);
        }
    }
}

class BlackEdit {
    constructor(editor_id, output_id, upload_url) {
        this.editor_id = editor_id;
        this.output_id = output_id;
        this.upload_url = upload_url;
    }

    getFrame(){
        if (_("#"+this.editor_id+"-space")[0].contentDocument){
            return _("#"+this.editor_id+"-space")[0].contentDocument;
        } else {
            return _("#"+this.editor_id+"-space")[0].contentWindow.document;
        }
    }

    getPopUp(toolbar_items, action_content, req) {
        var template = [
            `<div class="${this.editor_id}-popup" id="${this.editor_id}-popup">
            <div class="${this.editor_id}-popup-content" id='${this.editor_id}-popup-container'>
            <div class="${this.editor_id}-popup-topbar" id="${this.editor_id}-popup-toolbar">${toolbar_items}</div>
            `,
            `<div class="${this.editor_id}-popup-action-container" id="${this.editor_id}-popup-action-container">
            ${action_content}</div></div></div>`
        ]
    
        if (req) {
            return template[0];
        }
    
        return template[0] + template[1]
    }

    popup(ele, trigger, remove=false, self=this) {
    
        if (document.getElementById(`${self.editor_id}-popup`) != null) {
            document.getElementById(`${self.editor_id}-popup`).remove();
        }
        
        if (!remove) {
            let rect = ele.getBoundingClientRect();
            
            let unlink_items = `<div class="${this.editor_id}-popup-topbar-item" data-edcmd='unlink'><button><i class="fas fa-unlink"></i></button></div>
            <span style='font-size:14px; min-width:100px;'>${ele.href}</span>
            <div class="${this.editor_id}-popup-topbar-item" data-edcmd='insertLink' style="border-left:1px solid lightgray;" ><button><i class="fas fa-link"></i></button></div>
            `
    
            let popup_block = document.createElement('div');
            
            document.body.appendChild(popup_block);
            
            popup_block.innerHTML = this.getPopUp(unlink_items,'',true)
            
            let btns = _('[data-edcmd]');
            for (let b of btns) {
                b.addEventListener('click', function(event){
                    self.Run(b.dataset.edcmd, b, b.dataset.param);
                })
            }
    
            popup_block.style.cssText = `
            position:absolute;
            top:${rect.top+60}px;
            left:${rect.left + document.getElementById(`${this.editor_id}`).getBoundingClientRect().left - document.getElementById(`${this.editor_id}-popup`).getBoundingClientRect().width/2 + rect.width/2 }px;`
    
        }
    }

    Run(cmd,ele,value=null) {
        let status = this.getFrame().execCommand(cmd, false, value);
        let block;
        if (!status) {
            switch (cmd) {
                case 'code':
                    block = '<div style="background:lightgray;padding:6px;"><pre style="padding:0;">Your code here...</pre></div><br><p> </p>'
                    this.getFrame().execCommand('insertHtml', false, block)
                    break;

                case 'insertLink':
                    value = prompt('Enter url');
                    if (value.slice(0,4) != 'http') {value = 'http://' + value;}
                    this.getFrame().execCommand('createLink', false, value);
                    break;
                case 'insertPhoto':
                    value = _(`#${this.editor_id}-img-url`)[0].value;
                    this.getFrame().execCommand('insertImage', false, value);
                    this.getFrame().execCommand('insertParagraph', false, null);
                    console.log('photo')
                
            }   
        }
        
        console.log(status)
    }

    setStructure() {
        var editor_body = `<div class="${this.editor_id}-container"><div class="${this.editor_id}" id="${this.editor_id}"><div class="${this.editor_id}-tools"><div class="${this.editor_id}-basic-tools"> <button class="${this.editor_id}-btn" type="button" data-edcmd="bold"><i class="fas fa-bold"></i></button> <button class="${this.editor_id}-btn" type="button" data-edcmd="italic"><i class="fas fa-italic"></i></button> <button class="${this.editor_id}-btn" type="button" data-edcmd="underline"><i class="fas fa-underline"></i></button><div class="${this.editor_id}-dropdown m-view"> <button type="button" class="${this.editor_id}-dropdown-toggler ${this.editor_id}-btn"><i class="fas fa-font"></i></button><div class="${this.editor_id}-dropdown-toggle color-pallet-toggle"><div class="${this.editor_id}-dropdown-item color-pallet be-active" data-edcmd="foreColor" data-param="#FF0000" style="background-color:#FF0000"></div><div class="${this.editor_id}-dropdown-item color-pallet" data-edcmd="foreColor" data-param="#FF8000" style="background-color:#FF8000"></div><div class="${this.editor_id}-dropdown-item color-pallet" data-edcmd="foreColor" data-param="#FFFF00" style="background-color:#FFFF00"></div><div class="${this.editor_id}-dropdown-item color-pallet" data-edcmd="foreColor" data-param="#80FF00" style="background-color:#80FF00"></div><div class="${this.editor_id}-dropdown-item color-pallet" data-edcmd="foreColor" data-param="#00FF00" style="background-color:#00FF00"></div><div class="${this.editor_id}-dropdown-item color-pallet" data-edcmd="foreColor" data-param="#0080FF" style="background-color:#0080FF"></div><div class="${this.editor_id}-dropdown-item color-pallet" data-edcmd="foreColor" data-param="#0000FF" style="background-color:#0000FF"></div><div class="${this.editor_id}-dropdown-item color-pallet" data-edcmd="foreColor" data-param="#8000FF" style="background-color:#8000FF"></div><div class="${this.editor_id}-dropdown-item color-pallet" data-edcmd="foreColor" data-param="#FF00FF" style="background-color:#FF00FF"></div><div class="${this.editor_id}-dropdown-item color-pallet" data-edcmd="foreColor" data-param="#FF0080" style="background-color:#FF0080"></div><div class="${this.editor_id}-dropdown-item color-pallet" data-edcmd="foreColor" data-param="#000000" style="background-color:#000000"></div><div class="${this.editor_id}-dropdown-item color-pallet" data-edcmd="foreColor" data-param="#ffffff" style="background-color:#ffffff"></div></div></div><div class="${this.editor_id}-dropdown m-view"> <button type="button" class="${this.editor_id}-dropdown-toggler ${this.editor_id}-btn"><i class="fas fa-highlighter"></i></button><div class="${this.editor_id}-dropdown-toggle color-pallet-toggle"><div class="${this.editor_id}-dropdown-item color-pallet" data-edcmd="backColor" data-param="#FF0000" style="background-color:#FF0000"></div><div class="${this.editor_id}-dropdown-item color-pallet" data-edcmd="backColor" data-param="#FF8000" style="background-color:#FF8000"></div><div class="${this.editor_id}-dropdown-item color-pallet" data-edcmd="backColor" data-param="#FFFF00" style="background-color:#FFFF00"></div><div class="${this.editor_id}-dropdown-item color-pallet" data-edcmd="backColor" data-param="#80FF00" style="background-color:#80FF00"></div><div class="${this.editor_id}-dropdown-item color-pallet" data-edcmd="backColor" data-param="#00FF00" style="background-color:#00FF00"></div><div class="${this.editor_id}-dropdown-item color-pallet" data-edcmd="backColor" data-param="#0080FF" style="background-color:#0080FF"></div><div class="${this.editor_id}-dropdown-item color-pallet" data-edcmd="backColor" data-param="#0000FF" style="background-color:#0000FF"></div><div class="${this.editor_id}-dropdown-item color-pallet" data-edcmd="backColor" data-param="#8000FF" style="background-color:#8000FF"></div><div class="${this.editor_id}-dropdown-item color-pallet" data-edcmd="backColor" data-param="#FF00FF" style="background-color:#FF00FF"></div><div class="${this.editor_id}-dropdown-item color-pallet" data-edcmd="backColor" data-param="#FF0080" style="background-color:#FF0080"></div><div class="${this.editor_id}-dropdown-item color-pallet" data-edcmd="backColor" data-param="#000000" style="background-color:#000000"></div><div class="${this.editor_id}-dropdown-item color-pallet" data-edcmd="backColor" data-param="#ffffff" style="background-color:#ffffff"></div></div></div><div class="${this.editor_id}-dropdown"> <button type="button" class="${this.editor_id}-dropdown-toggler ${this.editor_id}-btn no-caret"><i class="fas fa-image"></i></button><div class="${this.editor_id}-dropdown-toggle"><form action="" method="post" id="${this.editor_id}-img-form" enctype="multipart/form-data"> <label for="${this.editor_id}-img-file"> <b>Drop image</b><br>(or click) </label> <input type="file" name="img" hidden id="${this.editor_id}-img-file" accept=".gif,.jpg,.jpeg,.png"></form><div class="url-input"> <input type="url" name="imgurl" id="${this.editor_id}-img-url" placeholder="http:// img url"> <button class="${this.editor_id}-btn" data-edcmd="insertPhoto">Insert</button></div></div></div></div><div class="${this.editor_id}-advance-tools"><div class="${this.editor_id}-dropdown"> <button type="button" class="${this.editor_id}-dropdown-toggler ${this.editor_id}-btn"><i class="fas fa-paragraph"></i></button><div class="${this.editor_id}-dropdown-toggle"><div class="${this.editor_id}-dropdown-item" data-edcmd="formatBlock" data-param="<p>"><p>Normal</p></div><div class="${this.editor_id}-dropdown-item" data-edcmd="formatBlock" data-param="<h1>"><h1>Heading 1</h1></div><div class="${this.editor_id}-dropdown-item" data-edcmd="formatBlock" data-param="<h2>"><h2>heading 2</h2></div><div class="${this.editor_id}-dropdown-item" data-edcmd="formatBlock" data-param="<h3>"><h3>Heading 3</h3></div><div class="${this.editor_id}-dropdown-item" data-edcmd="formatBlock" data-param="<h4>"><h4>Heading 4</h4></div><div class="${this.editor_id}-dropdown-item" data-edcmd="formatBlock" data-param="<h5>"><h5>Heading 5</h5></div><div class="${this.editor_id}-dropdown-item" data-edcmd="formatBlock" data-param="<blockquote>"><blockquote>Quote</blockquote></div></div></div><div class="${this.editor_id}-dropdown"> <button type="button" class="${this.editor_id}-dropdown-toggler ${this.editor_id}-btn"><i class="fas fa-align-left"></i></button><div class="${this.editor_id}-dropdown-toggle"><div class="${this.editor_id}-dropdown-item" data-edcmd="justifyLeft"><i class="fas fa-align-left"> &nbsp;left</i></div><div class="${this.editor_id}-dropdown-item" data-edcmd="justifyCenter"><i class="fas fa-align-center"> &nbsp;center</i></div><div class="${this.editor_id}-dropdown-item" data-edcmd="justifyRight"><i class="fas fa-align-right"> &nbsp;right</i></div><div class="${this.editor_id}-dropdown-item" data-edcmd="justifyFull"><i class="fas fa-align-justify"> &nbsp;justify</i></div></div></div><div class="${this.editor_id}-dropdown"> <button type="button" class="${this.editor_id}-dropdown-toggler ${this.editor_id}-btn" data-toggle='editor-dropdown' data-id='editor-dropdown-toggle-3'><i class="fas fa-list-ol"></i></button><div class="${this.editor_id}-dropdown-toggle" id="editor-dropdown-toggle-3"><div class="${this.editor_id}-dropdown-item" data-edcmd="insertOrderedList"><i class="fas fa-list-ol"> &nbsp;num list</i></div><div class="${this.editor_id}-dropdown-item" data-edcmd="insertUnorderedList"><i class="fas fa-list-ul"> &nbsp;bullet list</i></div></div></div> <button class="${this.editor_id}-btn" type="button" data-edcmd="insertLink"><i class="fas fa-link"></i></button> <button class="${this.editor_id}-btn" type="button" data-edcmd="code"><i class="fas fa-code"></i></button> <button class="${this.editor_id}-btn" type="button" data-edcmd="undo"><i class="fas fa-undo"></i></button> <button class="${this.editor_id}-btn" type="button" data-edcmd="redo"><i class="fas fa-redo"></i></button></div></div> <iframe id="${this.editor_id}-space" onblur="this.focus()" class="${this.editor_id}-space"></iframe> <span id="${this.editor_id}-count">0</span></div></div>`
        var editor_style = `<style>@font-face{font-family:'Roboto';font-style:normal;src:url(https://fonts.googleapis.com/css?family=Roboto)}.${this.editor_id}-frame-manager img{width:inherit;min-width:200px}.${this.editor_id}-container{display:inline-block;position:relative;width:100%;}.${this.editor_id}-container .${this.editor_id}{font-family:'Roboto',sans-serif;display:block;width:100%;-webkit-box-shadow:0 0.25rem 0.5rem gray;box-shadow:0 0.25rem 0.5rem gray;resize:both}.${this.editor_id}-container .${this.editor_id} .${this.editor_id}-btn{font-size:12px;border:0px;padding:5px 8px;margin:0;cursor:pointer;background:white}.${this.editor_id}-container .${this.editor_id} .${this.editor_id}-btn:hover{outline:none;background:lightgray}.${this.editor_id}-container .${this.editor_id} .${this.editor_id}-btn.be-active{color:dodgerblue}.${this.editor_id}-container .${this.editor_id} .${this.editor_id}-tools{padding:auto 10px;width:100%;border-top:5px solid dodgerblue;display:-webkit-box;display:-ms-flexbox;display:flex;-webkit-box-orient:horizontal;-webkit-box-direction:normal;-ms-flex-direction:row; flex-direction:row;-webkit-box-pack:center;-ms-flex-pack:center;justify-content:center;border-bottom:1px solid lightgray}.${this.editor_id}-container .${this.editor_id} .${this.editor_id}-tools .${this.editor_id}-basic-tools{display:-webkit-box;display:-ms-flexbox;width:100%;justify-content:center;display:flex;border-right:1px solid lightgray}.${this.editor_id}-container .${this.editor_id} .${this.editor_id}-tools .${this.editor_id}-advance-tools{display:-webkit-box;display:-ms-flexbox;display:flex; justify-content:center;width:100%;}.${this.editor_id}-container .${this.editor_id} .${this.editor_id}-space{font-family:sans-serif;position:relative;display:inline-block;width:100%;min-height:300px;-webkit-box-sizing:border-box;box-sizing:border-box;padding:5px;overflow-wrap:break-word;background:white;border:0}.${this.editor_id}-container .${this.editor_id} #${this.editor_id}-count{position:absolute;border-top:1px solid lightgray;border-left:1px solid lightgray;padding:2px 5px 0 5px;font-size:0.8rem;color:gray;font-weight:100;bottom:0;right:0}.${this.editor_id}-container .${this.editor_id}-dropdown{display:inline-block;position:relative}.${this.editor_id}-container .${this.editor_id}-dropdown:hover .${this.editor_id}-dropdown-toggler{color:dodgerblue}.${this.editor_id}-container .${this.editor_id}-dropdown:hover .${this.editor_id}-dropdown-toggle{display:block}.${this.editor_id}-container .${this.editor_id}-dropdown:hover .${this.editor_id}-dropdown-toggle.color-pallet-toggle{display:-webkit-box;display:-ms-flexbox;display:flex;-ms-flex-wrap:wrap;flex-wrap:wrap;min-width:126px;padding:3px}.${this.editor_id}-container .${this.editor_id}-dropdown .${this.editor_id}-dropdown-toggler::after{content:"";border-top:4px solid black;border-bottom:4px solid transparent;border-left:4px solid transparent;border-right:4px solid transparent;position:absolute;top:13px;}.${this.editor_id}-container .${this.editor_id}-dropdown .${this.editor_id}-dropdown-toggler.no-caret::after{display:none}.${this.editor_id}-container .${this.editor_id}-dropdown .${this.editor_id}-dropdown-toggle{z-index:1;font-family:sans-serif;display:none;position:absolute;background-color:white;border-bottom-left-radius:2px;border-bottom-right-radius:2px;-webkit-box-shadow:0px 8px 16px 0px rgba(0, 0, 0, 0.2);box-shadow:0px 8px 16px 0px rgba(0, 0, 0, 0.2)}.${this.editor_id}-container .${this.editor_id}-dropdown .${this.editor_id}-dropdown-toggle.${this.editor_id}-hover-dropdown{display:block}.${this.editor_id}-container .${this.editor_id}-dropdown .${this.editor_id}-dropdown-toggle .${this.editor_id}-dropdown-item{cursor:pointer;padding:5px 10px}.${this.editor_id}-container .${this.editor_id}-dropdown .${this.editor_id}-dropdown-toggle .${this.editor_id}-dropdown-item p, .${this.editor_id}-container .${this.editor_id}-dropdown .${this.editor_id}-dropdown-toggle .${this.editor_id}-dropdown-item h1, .${this.editor_id}-container .${this.editor_id}-dropdown .${this.editor_id}-dropdown-toggle .${this.editor_id}-dropdown-item h2, .${this.editor_id}-container .${this.editor_id}-dropdown .${this.editor_id}-dropdown-toggle .${this.editor_id}-dropdown-item h3, .${this.editor_id}-container .${this.editor_id}-dropdown .${this.editor_id}-dropdown-toggle .${this.editor_id}-dropdown-item h4, .${this.editor_id}-container .${this.editor_id}-dropdown .${this.editor_id}-dropdown-toggle .${this.editor_id}-dropdown-item h5, .${this.editor_id}-dropdown-item blockquote{margin:0;padding:0;min-width:200px}.${this.editor_id}-container .${this.editor_id}-dropdown .${this.editor_id}-dropdown-toggle .${this.editor_id}-dropdown-item i{min-width:100px;padding:auto 40px}.${this.editor_id}-container .${this.editor_id}-dropdown .${this.editor_id}-dropdown-toggle .${this.editor_id}-dropdown-item:hover{background:lightgray}.${this.editor_id}-container .${this.editor_id}-dropdown .${this.editor_id}-dropdown-toggle .${this.editor_id}-dropdown-item.be-active{background:lightgray;opacity:0.8}.${this.editor_id}-container .${this.editor_id}-dropdown .${this.editor_id}-dropdown-toggle .${this.editor_id}-dropdown-item.color-pallet{font-weight:600;text-align:center;display:inline-block;min-width:36px;min-height:35px;margin:2px;outline:1px dotted gray}.${this.editor_id}-container .${this.editor_id}-dropdown .${this.editor_id}-dropdown-toggle:last-child{border-bottom:2px solid dodgerblue}.${this.editor_id}-container .${this.editor_id}-dropdown .${this.editor_id}-dropdown-toggle form{display:-webkit-box;display:-ms-flexbox;display:flex;width:200px;height:100px}.${this.editor_id}-container .${this.editor_id}-dropdown .${this.editor_id}-dropdown-toggle form label{text-align:center;padding-top:25px;cursor:pointer;width:100%;outline:1.5px dashed gray;margin:10px}.${this.editor_id}-container .${this.editor_id}-dropdown .${this.editor_id}-dropdown-toggle form label:hover{background:#eee}.${this.editor_id}-container .${this.editor_id}-dropdown .${this.editor_id}-dropdown-toggle .url-input{display:-webkit-box;display:-ms-flexbox;display:flex;-webkit-box-orient:vertical;-webkit-box-direction:normal;-ms-flex-direction:column;flex-direction:column}.${this.editor_id}-container .${this.editor_id}-dropdown .${this.editor_id}-dropdown-toggle .url-input input{display:block;outline:none;border:0;border-bottom:1px solid dodgerblue;margin:auto 10px}.${this.editor_id}-popup{background:white;margin:0 auto;position:relative;display:inline-block;min-width:100px;border:1px solid lightgray;border-top:4px solid dodgerblue;-webkit-box-sizing:border-box;box-sizing:border-box;-webkit-box-shadow:0 0.25rem 0.5rem gray;box-shadow:0 0.25rem 0.5rem gray}.${this.editor_id}-popup::after{position:absolute;content:"";border-top:7px solid transparent;border-bottom:7px solid dodgerblue;border-left:7px solid transparent;border-right:7px solid transparent;top:-17px;left:47%}.${this.editor_id}-popup .${this.editor_id}-popup-content{position:relative;display:inline-block;width:100%;height:100%}.${this.editor_id}-popup .${this.editor_id}-popup-content .${this.editor_id}-popup-topbar{display:-webkit-box;display:-ms-flexbox;display:flex;width:100%;height:24%;border-bottom:1px solid lightgray}.${this.editor_id}-popup .${this.editor_id}-popup-content .${this.editor_id}-popup-topbar .${this.editor_id}-popup-topbar-item{display:-webkit-box;display:-ms-flexbox;display:flex;height:35px;width:35px;border-right:1px solid lightgray}.${this.editor_id}-popup .${this.editor_id}-popup-content .${this.editor_id}-popup-topbar .${this.editor_id}-popup-topbar-item button{cursor:pointer;width:100%;height:100%;border:0;background:white;outline:none}.${this.editor_id}-popup .${this.editor_id}-popup-content .${this.editor_id}-popup-topbar .${this.editor_id}-popup-topbar-item button:hover{background:lightgray}.${this.editor_id}-popup .${this.editor_id}-popup-content .${this.editor_id}-popup-topbar .${this.editor_id}-popup-topbar-item button.be-active{color:dodgerblue}.${this.editor_id}-popup .${this.editor_id}-popup-content .${this.editor_id}-popup-action-container{display:-webkit-box;display:-ms-flexbox;display:flex;width:100%;height:76%}.${this.editor_id}-popup-content-wrapper{display:inline-block;width:100%}.${this.editor_id}-popup-form{display:-webkit-box;display:-ms-flexbox;display:flex;width:100%;height:100%}.${this.editor_id}-popup-form label{text-align:center;padding-top:25px;cursor:pointer;width:100%;outline:1.5px dashed gray;margin:10px}.${this.editor_id}-popup-form label:hover{background:#eee}.${this.editor_id}-popup-input{display:inline-block;margin:12px;width:90%;height:20px;padding:0;border:0;border-bottom:2px solid dodgerblue}.${this.editor_id}-popup-action-url-btn{cursor:pointer;font-size:16px;float:right;padding:5px 15px;background:transparent;border:0}.${this.editor_id}-popup-action-url-btn:hover{background:#eee;color:dodgerblue}@media(max-width:720px){.${this.editor_id}-dropdown.m-view{display:none;}}</style>`
        // var editor_style = '';
        document.head.innerHTML += editor_style;
        _(`#${this.editor_id}`)[0].innerHTML = editor_body;
    }

    stateAgent() {
        document.getElementById(`${this.editor_id}-count`).innerText = this.getFrame().body.innerText.length;
        for (let b of _('[data-edcmd]')) {
            let state = this.getFrame().queryCommandState(b.dataset.edcmd);
            if (state) {b.classList.add('be-active');}
            else {b.classList.remove('be-active');}
        }
    }

    startEdit() {
        this.getFrame().designMode="on";
    }

    setEvents(self=this) {
        // state Agent 
        this.getFrame().addEventListener('mouseup', function(){self.stateAgent();});
        this.getFrame().addEventListener('keyup', function(){self.stateAgent();});

        // command triggers
        for (let b of _('[data-edcmd]')) {
            b.addEventListener('click', function(event){
                self.Run(b.dataset.edcmd, b, b.dataset.param);
                _(`#${self.editor_id}-space`)[0].focus();
                self.getFrame().body.focus();
            })
        }

        this.getFrame().addEventListener('click',function(e){
            window.scrollBy(0,document.getElementById(`${self.editor_id}`).getBoundingClientRect().top);
            document.getElementById(`${self.editor_id}-img-form`).parentNode.classList.remove('be-hover-dropdown');
            
            if (e.target.tagName == 'A') {
                self.popup(e.target, '')
            }
            else {
                self.popup('', '', true);
            }
        
        }, true);

        $(document).on('change',`#${self.editor_id}-img-file` ,function(ev){
    
            var ofile=ev.target.files[0];
            var formdata = new FormData();
            formdata.append("image",ofile);
        
            $.ajax({
                url: self.upload_url,
                method:'post',
                processData: false, 
                contentType: false,
                data:formdata,
                success: function(re) {
                    _(`#${self.editor_id}-img-file`)[0].value = re.url;
                    run('insertImage','', re.url)
                    console.log(re);
                },
                error: function(re) {
                    console.log('woo hooo');
                }
            })
        })
    }

    getOutput(auto=false) {
        if (auto) {
            this.getFrame().addEventListener('keyup', ()=>{
                _(`#${this.output_id}`)[0].innerText = this.getFrame().body.innerHTML;
            });
            
            _(`#${this.output_id}`)[0].addEventListener('keyup', ()=>{
                this.getFrame().body.innerHTML = _(`#${this.output_id}`)[0].innerText;
            });
        }
        return this.getFrame().body.innerHTML;
    }

    init(auto_output=false) {
        this.setStructure();
        _(`#${this.output_id}`)[0].contentEditable = true;
        
        setTimeout(()=>{
            this.setEvents();
            this.startEdit();
            auto_output ? this.getOutput(auto_output):'';
        }, 100); 
    }
}
