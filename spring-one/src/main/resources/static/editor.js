var html = `<div class="be-container"><div class="be" id="be"><div class="be-tools"><div class="be-basic-tools"> <button class="be-btn" type="button" data-edcmd="bold"><i class="fas fa-bold"></i></button> <button class="be-btn" type="button" data-edcmd="italic"><i class="fas fa-italic"></i></button> <button class="be-btn" type="button" data-edcmd="underline"><i class="fas fa-underline"></i></button><div class="be-dropdown"> <button type="button" class="be-dropdown-toggler be-btn"><i class="fas fa-font"></i></button><div class="be-dropdown-toggle color-pallet-toggle"><div class="be-dropdown-item color-pallet active" data-edcmd="foreColor" data-param="#FF0000" style="background-color:#FF0000"></div><div class="be-dropdown-item color-pallet" data-edcmd="foreColor" data-param="#FF8000" style="background-color:#FF8000"></div><div class="be-dropdown-item color-pallet" data-edcmd="foreColor" data-param="#FFFF00" style="background-color:#FFFF00"></div><div class="be-dropdown-item color-pallet" data-edcmd="foreColor" data-param="#80FF00" style="background-color:#80FF00"></div><div class="be-dropdown-item color-pallet" data-edcmd="foreColor" data-param="#00FF00" style="background-color:#00FF00"></div><div class="be-dropdown-item color-pallet" data-edcmd="foreColor" data-param="#0080FF" style="background-color:#0080FF"></div><div class="be-dropdown-item color-pallet" data-edcmd="foreColor" data-param="#0000FF" style="background-color:#0000FF"></div><div class="be-dropdown-item color-pallet" data-edcmd="foreColor" data-param="#8000FF" style="background-color:#8000FF"></div><div class="be-dropdown-item color-pallet" data-edcmd="foreColor" data-param="#FF00FF" style="background-color:#FF00FF"></div><div class="be-dropdown-item color-pallet" data-edcmd="foreColor" data-param="#FF0080" style="background-color:#FF0080"></div><div class="be-dropdown-item color-pallet" data-edcmd="foreColor" data-param="#000000" style="background-color:#000000"></div><div class="be-dropdown-item color-pallet" data-edcmd="foreColor" data-param="#ffffff" style="background-color:#ffffff"></div></div></div><div class="be-dropdown"> <button type="button" class="be-dropdown-toggler be-btn"><i class="fas fa-highlighter"></i></button><div class="be-dropdown-toggle color-pallet-toggle"><div class="be-dropdown-item color-pallet" data-edcmd="backColor" data-param="#FF0000" style="background-color:#FF0000"></div><div class="be-dropdown-item color-pallet" data-edcmd="backColor" data-param="#FF8000" style="background-color:#FF8000"></div><div class="be-dropdown-item color-pallet" data-edcmd="backColor" data-param="#FFFF00" style="background-color:#FFFF00"></div><div class="be-dropdown-item color-pallet" data-edcmd="backColor" data-param="#80FF00" style="background-color:#80FF00"></div><div class="be-dropdown-item color-pallet" data-edcmd="backColor" data-param="#00FF00" style="background-color:#00FF00"></div><div class="be-dropdown-item color-pallet" data-edcmd="backColor" data-param="#0080FF" style="background-color:#0080FF"></div><div class="be-dropdown-item color-pallet" data-edcmd="backColor" data-param="#0000FF" style="background-color:#0000FF"></div><div class="be-dropdown-item color-pallet" data-edcmd="backColor" data-param="#8000FF" style="background-color:#8000FF"></div><div class="be-dropdown-item color-pallet" data-edcmd="backColor" data-param="#FF00FF" style="background-color:#FF00FF"></div><div class="be-dropdown-item color-pallet" data-edcmd="backColor" data-param="#FF0080" style="background-color:#FF0080"></div><div class="be-dropdown-item color-pallet" data-edcmd="backColor" data-param="#000000" style="background-color:#000000"></div><div class="be-dropdown-item color-pallet" data-edcmd="backColor" data-param="#ffffff" style="background-color:#ffffff"></div></div></div><div class="be-dropdown"> <button type="button" class="be-dropdown-toggler be-btn no-caret"><i class="fas fa-image"></i></button><div class="be-dropdown-toggle"><form action="" method="post" id="be-img-form" enctype="multipart/form-data"> <label for="be-img-file"> <b>Drop image</b><br>(or click) </label> <input type="file" name="img" hidden id="be-img-file" accept=".gif,.jpg,.jpeg,.png"></form><div class="url-input"> <input type="url" name="imgurl" id="be-img-url" placeholder="http:// img url"> <button class="be-btn" onclick="insertImage('be-img-url')">Insert</button></div></div></div></div><div class="be-advance-tools"><div class="be-dropdown"> <button type="button" class="be-dropdown-toggler be-btn"><i class="fas fa-paragraph"></i></button><div class="be-dropdown-toggle"><div class="be-dropdown-item" data-edcmd="formatBlock" data-param="<p>"><p>Normal</p></div><div class="be-dropdown-item" data-edcmd="formatBlock" data-param="<h1>"><h1>Heading 1</h1></div><div class="be-dropdown-item" data-edcmd="formatBlock" data-param="<h2>"><h2>heading 2</h2></div><div class="be-dropdown-item" data-edcmd="formatBlock" data-param="<h3>"><h3>Heading 3</h3></div><div class="be-dropdown-item" data-edcmd="formatBlock" data-param="<h4>"><h4>Heading 4</h4></div><div class="be-dropdown-item" data-edcmd="formatBlock" data-param="<h5>"><h5>Heading 5</h5></div></div></div><div class="be-dropdown"> <button type="button" class="be-dropdown-toggler be-btn"><i class="fas fa-align-left"></i></button><div class="be-dropdown-toggle"><div class="be-dropdown-item" data-edcmd="justifyLeft"><i class="fas fa-align-left"> &nbsp;left</i></div><div class="be-dropdown-item" data-edcmd="justifyCenter"><i class="fas fa-align-center"> &nbsp;center</i></div><div class="be-dropdown-item" data-edcmd="justifyRight"><i class="fas fa-align-right"> &nbsp;right</i></div><div class="be-dropdown-item" data-edcmd="justifyFull"><i class="fas fa-align-justify"> &nbsp;justify</i></div></div></div><div class="be-dropdown"> <button type="button" class="be-dropdown-toggler be-btn" data-toggle='editor-dropdown' data-id='editor-dropdown-toggle-3'><i class="fas fa-list-ol"></i></button><div class="be-dropdown-toggle" id="editor-dropdown-toggle-3"><div class="be-dropdown-item" data-edcmd="insertOrderedList"><i class="fas fa-list-ol"> &nbsp;num list</i></div><div class="be-dropdown-item" data-edcmd="insertUnorderedList"><i class="fas fa-list-ul"> &nbsp;bullet list</i></div></div></div> <button class="be-btn" type="button" data-edcmd="insertLink"><i class="fas fa-link"></i></button> <button class="be-btn" type="button" data-edcmd="code"><i class="fas fa-code"></i></button> <button class="be-btn" type="button" data-edcmd="undo"><i class="fas fa-undo"></i></button> <button class="be-btn" type="button" data-edcmd="redo"><i class="fas fa-redo"></i></button></div></div> <iframe id="be-space" onblur="this.focus()" class="be-space"></iframe> <span id="be-count">0</span></div></div>`
var css = `<style>@font-face{font-family:'Roboto';font-style:normal;src:url(https://fonts.googleapis.com/css?family=Roboto)}.be-frame-manager img{width:inherit;min-width:200px}.be-container{display:inline-block;position:relative;width:500px}.be-container .be{font-family:'Roboto',sans-serif;display:block;width:100%;-webkit-box-shadow:0 0.25rem 0.5rem gray;box-shadow:0 0.25rem 0.5rem gray;resize:both}.be-container .be .be-btn{font-size:12px;border:0px;padding:5px 12px;margin:0;cursor:pointer;background:white}.be-container .be .be-btn:hover{outline:none;background:lightgray}.be-container .be .be-btn.active{color:dodgerblue}.be-container .be .be-tools{padding:auto 10px;width:100%;border-top:5px solid dodgerblue;display:-webkit-box;display:-ms-flexbox;display:flex;-webkit-box-orient:horizontal;-webkit-box-direction:normal;-ms-flex-direction:row;flex-direction:row;-webkit-box-pack:center;-ms-flex-pack:center;justify-content:center;border-bottom:1px solid lightgray}.be-container .be .be-tools .be-basic-tools{display:-webkit-box;display:-ms-flexbox;display:flex;border-right:1px solid lightgray}.be-container .be .be-tools .be-advance-tools{display:-webkit-box;display:-ms-flexbox;display:flex}.be-container .be .be-space{font-family:sans-serif;position:relative;display:inline-block;width:100%;min-height:300px;-webkit-box-sizing:border-box;box-sizing:border-box;padding:5px;overflow-wrap:break-word;background:white;border:0}.be-container .be #be-count{position:absolute;border-top:1px solid lightgray;border-left:1px solid lightgray;padding:2px 5px 0 5px;font-size:0.8rem;color:gray;font-weight:100;bottom:0;right:0}.be-container .be-dropdown{display:inline-block;position:relative}.be-container .be-dropdown:hover .be-dropdown-toggler{color:dodgerblue}.be-container .be-dropdown:hover .be-dropdown-toggle{display:block}.be-container .be-dropdown:hover .be-dropdown-toggle.color-pallet-toggle{display:-webkit-box;display:-ms-flexbox;display:flex;-ms-flex-wrap:wrap;flex-wrap:wrap;min-width:126px;padding:3px}.be-container .be-dropdown .be-dropdown-toggler::after{content:"";border-top:4px solid black;border-bottom:4px solid transparent;border-left:4px solid transparent;border-right:4px solid transparent;position:absolute;top:13px;right:2px}.be-container .be-dropdown .be-dropdown-toggler.no-caret::after{display:none}.be-container .be-dropdown .be-dropdown-toggle{z-index:1;font-family:sans-serif;display:none;position:absolute;background-color:white;border-bottom-left-radius:2px;border-bottom-right-radius:2px;-webkit-box-shadow:0px 8px 16px 0px rgba(0, 0, 0, 0.2);box-shadow:0px 8px 16px 0px rgba(0, 0, 0, 0.2)}.be-container .be-dropdown .be-dropdown-toggle.be-hover-dropdown{display:block}.be-container .be-dropdown .be-dropdown-toggle .be-dropdown-item{cursor:pointer;padding:5px 10px}.be-container .be-dropdown .be-dropdown-toggle .be-dropdown-item p, .be-container .be-dropdown .be-dropdown-toggle .be-dropdown-item h1, .be-container .be-dropdown .be-dropdown-toggle .be-dropdown-item h2, .be-container .be-dropdown .be-dropdown-toggle .be-dropdown-item h3, .be-container .be-dropdown .be-dropdown-toggle .be-dropdown-item h4, .be-container .be-dropdown .be-dropdown-toggle .be-dropdown-item h5{margin:0;padding:0;min-width:200px}.be-container .be-dropdown .be-dropdown-toggle .be-dropdown-item i{min-width:100px;padding:auto 40px}.be-container .be-dropdown .be-dropdown-toggle .be-dropdown-item:hover{background:lightgray}.be-container .be-dropdown .be-dropdown-toggle .be-dropdown-item.active{background:lightgray;opacity:0.8}.be-container .be-dropdown .be-dropdown-toggle .be-dropdown-item.color-pallet{font-weight:600;text-align:center;display:inline-block;min-width:36px;min-height:35px;margin:2px;outline:1px dotted gray}.be-container .be-dropdown .be-dropdown-toggle:last-child{border-bottom:2px solid dodgerblue}.be-container .be-dropdown .be-dropdown-toggle form{display:-webkit-box;display:-ms-flexbox;display:flex;width:200px;height:100px}.be-container .be-dropdown .be-dropdown-toggle form label{text-align:center;padding-top:25px;cursor:pointer;width:100%;outline:1.5px dashed gray;margin:10px}.be-container .be-dropdown .be-dropdown-toggle form label:hover{background:#eee}.be-container .be-dropdown .be-dropdown-toggle .url-input{display:-webkit-box;display:-ms-flexbox;display:flex;-webkit-box-orient:vertical;-webkit-box-direction:normal;-ms-flex-direction:column;flex-direction:column}.be-container .be-dropdown .be-dropdown-toggle .url-input input{display:block;outline:none;border:0;border-bottom:1px solid dodgerblue;margin:auto 10px}.be-popup{background:white;margin:0 auto;position:relative;display:inline-block;min-width:100px;border:1px solid lightgray;border-top:4px solid dodgerblue;-webkit-box-sizing:border-box;box-sizing:border-box;-webkit-box-shadow:0 0.25rem 0.5rem gray;box-shadow:0 0.25rem 0.5rem gray}.be-popup::after{position:absolute;content:"";border-top:7px solid transparent;border-bottom:7px solid dodgerblue;border-left:7px solid transparent;border-right:7px solid transparent;top:-17px;left:47%}.be-popup .be-popup-content{position:relative;display:inline-block;width:100%;height:100%}.be-popup .be-popup-content .be-popup-topbar{display:-webkit-box;display:-ms-flexbox;display:flex;width:100%;height:24%;border-bottom:1px solid lightgray}.be-popup .be-popup-content .be-popup-topbar .be-popup-topbar-item{display:-webkit-box;display:-ms-flexbox;display:flex;height:35px;width:35px;border-right:1px solid lightgray}.be-popup .be-popup-content .be-popup-topbar .be-popup-topbar-item button{cursor:pointer;width:100%;height:100%;border:0;background:white;outline:none}.be-popup .be-popup-content .be-popup-topbar .be-popup-topbar-item button:hover{background:lightgray}.be-popup .be-popup-content .be-popup-topbar .be-popup-topbar-item button.active{color:dodgerblue}.be-popup .be-popup-content .be-popup-action-container{display:-webkit-box;display:-ms-flexbox;display:flex;width:100%;height:76%}.be-popup-content-wrapper{display:inline-block;width:100%}.be-popup-form{display:-webkit-box;display:-ms-flexbox;display:flex;width:100%;height:100%}.be-popup-form label{text-align:center;padding-top:25px;cursor:pointer;width:100%;outline:1.5px dashed gray;margin:10px}.be-popup-form label:hover{background:#eee}.be-popup-input{display:inline-block;margin:12px;width:90%;height:20px;padding:0;border:0;border-bottom:2px solid dodgerblue}.be-popup-action-url-btn{cursor:pointer;font-size:16px;float:right;padding:5px 15px;background:transparent;border:0}.be-popup-action-url-btn:hover{background:#eee;color:dodgerblue}</style>`

var btns = document.querySelectorAll('[data-edcmd]');

function getFrame(aID){
    if (document.getElementById(aID).contentDocument){
        return document.getElementById(aID).contentDocument;
    } else {
        return document.frames[aID].document;
    }
}

function trigger() {
    let space = getFrame('be-space')
    space.designMode = 'on';
    space.addEventListener('mouseup', agent);
    space.addEventListener('keyup', agent);
    
    //for buttons cmds
    for (let b of btns) {
        b.addEventListener('click', function(event){
            run(b.dataset.edcmd, b, b.dataset.param);
            document.getElementById('be-space').contentWindow.focus();
            document.getElementById('be-space').contentWindow.document.body.focus();
        })
    }

    // for dropdown
    let ed = document.querySelectorAll('.be-dropdown-item')
    for (let e of ed) {
        e.addEventListener('click',function(event){
            document.getElementById('be-space').contentWindow.focus();
            document.getElementById('be-space').contentWindow.document.body.focus();
        })
    }

    // for editor to be at top on focus
    let frame = getFrame('be-space');
    frame.addEventListener('click',function(e){
        window.scrollBy(0,document.getElementById('be').getBoundingClientRect().top);
        document.getElementById('be-img-form').parentNode.classList.remove('be-hover-dropdown');
        
        if (e.target.tagName == 'A') {
            popup(e.target, '')
        }
        else {
            popup('', '', true);
        }
    
    }, true);
    

    document.getElementById('be-img-form').addEventListener('click', function(){
        this.parentElement.classList.add('be-hover-dropdown');
    })
    
}

function run(cmd,ele,value=null) {
    let status = getFrame('be-space').execCommand(cmd, false, value);
    let block;
    if (!status) {
        switch (cmd) {
            case 'code':
                block = '<div style="background:lightgray;padding:10px;"><pre style="padding:0;">Your code here...</pre></div><p></p>'
                getFrame('be-space').execCommand('insertHtml', false, block)
                break;
            case 'insertLink':
                value = prompt('Enter url');
                if (value.slice(0,4) != 'http') {
                    value = 'http://' + value;
                }
                getFrame('be-space').execCommand('createLink', false, value);
                break;
        }
    }
    console.log(status)
}

function agent() {
    document.getElementById('be-count').innerText = getFrame('be-space').body.innerText.length;
    for (let b of btns) {
        let state = getFrame('be-space').queryCommandState(b.dataset.edcmd);
        if (state) {
            b.classList.add('active');

        }
        else {
            b.classList.remove('active');
        }
    }
}



// popup tools
function checkImageUrl(url) {
    return(url.match(/\.(jpeg|jpg|gif|png)/) != null);
}

function insertImage(id) {
    let url = document.getElementById(id).value;
    if(checkImageUrl(url)) {
        document.getElementById('be-space').contentWindow.focus();
        run('insertImage','',url)
    }
}

function previewFile(file) {
    var reader  = new FileReader();
  
    // reader.onloadend = function () {
    //   preview.src = reader.result;
    // }
  
    if (file) {
      console.log(reader.readAsDataURL(file));
    } else {
      preview.src = "";
    }
}


$('#be-img-file').on('change', function(ev){
    
    var ofile=ev.target.files[0];
    var formdata = new FormData();
    formdata.append("image",ofile);

    $.ajax({
        url:'http://192.168.0.103:8000/upload/',
        method:'post',
        processData: false, 
        contentType: false,
        data:formdata,
        success: function(re) {
            document.getElementById('be-space').contentWindow.document.body.focus();
            document.getElementById('be-img-url').value = "http://192.168.0.103:8000"+re.url;
            run('insertImage','',"http://192.168.0.103:8000"+re.url)
            console.log(re);
        },
        error: function(re) {
            console.log('woo hooo');
        }
    })
})


function getPopUp(toolbar_items, action_content, req) {
    var template = [
        `<div class="be-popup" id="be-popup">
        <div class="be-popup-content" id='be-popup-container'>
        <div class="be-popup-topbar" id="be-popup-toolbar">${toolbar_items}</div>
        `,
        `<div class="be-popup-action-container" id="be-popup-action-container">
        ${action_content}</div></div></div>`
    ]

    if (req) {
        return template[0];
    }

    return template[0] + template[1]
}
function getDime(ele) {
    return [window.getComputedStyle(ele,false).width, window.getComputedStyle(ele,false).height]
}

function popup(ele, trigger, remove=false) {
    
    if (document.getElementById('be-popup') != null) {
        document.getElementById('be-popup').remove();
    }
    
    if (!remove) {
        let rect = ele.getBoundingClientRect();
        let toolbar_items = `<div class="be-popup-topbar-item" data-switch-id='be-img-form'><button><i class="fas fa-upload"></i></button></div>
        <div class="be-popup-topbar-item" data-switch-id='be-url-input'><button><i class="fas fa-link"></i></button></div>
        <div class="be-popup-topbar-item" data-switch-id='be-action-model'><button><i class="fas fa-folder"></i></button></div>`
        
        let unlink_items = `<div class="be-popup-topbar-item" data-edcmd='unlink'><button><i class="fas fa-unlink"></i></button></div>
        <span style='font-size:14px; min-width:100px;'>${ele.href}</span>
        <div class="be-popup-topbar-item" data-edcmd='insertLink' style="border-left:1px solid lightgray;" ><button><i class="fas fa-link"></i></button></div>
        `

        let action_content = `<div class="be-popup-content-wrapper">
                <form action="" method="post" class="be-popup-form" id="be-img-form">
                    <label for="be-popup-action-img">
                        <b>Drop image</b><br>(or click)
                    </label>
                    <input type="file" name="img" hidden id="be-popup-upload-img">
                </form>
            </div>`

        let popup_block = document.createElement('div');
        // popup_block.classList.add('be-popup');
        // popup_block.id = 'be-popup';
        

        
        document.body.appendChild(popup_block);
        
        popup_block.innerHTML = getPopUp(unlink_items,'',true)
        
        btns = document.querySelectorAll('[data-edcmd]');
        for (let b of btns) {
            b.addEventListener('click', function(event){
                run(b.dataset.edcmd, b, b.dataset.param);
                document.getElementById('be-space').contentWindow.focus();
                document.getElementById('be-space').contentWindow.document.body.focus();
            })
        }

        popup_block.style.cssText = `
        position:absolute;
        top:${rect.top+60}px;
        left:${rect.left + document.getElementById('be').getBoundingClientRect().left - document.getElementById('be-popup').getBoundingClientRect().width/2 + rect.width/2 }px;`

    }
}

function popupInit() {
    let toggle = document.querySelectorAll('[data-toggle="be-popup"]');
    for (let t of toggle) {
        t.addEventListener('click',function(event){
            let rect = event.target.getBoundingClientRect();
            popup(rect, t.dataset.trigger);
        })
    }
}













window.onload = function(){
    trigger();
    this.document.getElementById('be-space').contentWindow.document.head.innerHTML += '<link href="./iframe.css" rel="stylesheet">'
    this.document.getElementById('be-space').contentWindow.document.body.style.cssText=`overflow-wrap:break-word; font-family:'Roboto', sans-serif;`;
    popupInit();
    
}


// class Editor {
//     constructor (editor_id, output_id, img_upload_link) {

//     }
// }