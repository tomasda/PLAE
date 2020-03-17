$(document).ready(function() {
    setTimeout(fileUpload, 1000);

})

var numberFiles = 0;

function previous(){
	numberFiles = PF('uploaderDocumentos').files.length;
}
			
function end(){
	if (PF('uploaderDocumentos').uploadedFileCount == numberFiles){
		PF('uploaderDocumentos').uploadedFileCount = 0;
		$(PF('buttonPersonal').jqId).click();
	}
}

function fileUpload() {
   
    PF('uploaderDocumentos').jq.fileupload({
        add: function(e, data) {
            $this = PF('uploaderDocumentos');
            $this.chooseButton.removeClass('ui-state-hover ui-state-focus');
            if ($this.files.length === 0) {
                $this.enableButton($this.uploadButton);
                $this.enableButton($this.cancelButton);
            }

            if ($this.cfg.fileLimit && ($this.uploadedFileCount + $this.files.length + 1) > $this.cfg.fileLimit) {
                $this.clearMessages();
                $this.showMessage({
                    summary: $this.cfg.fileLimitMessage
                });

                return;
            }

            var file = data.files ? data.files[0] : null;
            if (file) {
                var validMsg = $this.validate(file);

                if (validMsg) {
                    $this.showMessage({
                        summary: validMsg,
                        filename: file.name,
                        filesize: file.size
                    });
                }
                else {
                    $this.clearMessages();                                                                               

                    var row = $('<tr></tr>').append('<td class="ui-fileupload-preview"></td>')
                            .append('<td style="vertical-align:top">' + file.name + ' (' + $this.formatSize(file.size) + ')</td>')
                            //.append('<td class="title"><label>Title: <input name="title['+ file.name +']"></label></td>') //the only modification we have to do
                            .append('<td style="vertical-align:top" class="title"><label>Asunto: </label></td>')
                            .append('<td style="vertical-align:top;width:30%"><textarea aria-multiline="true" aria-readonly="false" aria-disabled="false" role="textbox" name="title['+ file.name +']" cols="30" rows="5" class="ui-inputfield ui-inputtextarea ui-widget ui-state-default ui-corner-all inputAsunto ui-inputtextarea-resizable" maxlength="4000" style="font-size:0.8em;"></textarea></td>')
                            .append('<td style="vertical-align:top" class="ui-fileupload-progress"></td>')
                            .append('<td style="vertical-align:top"><button class="ui-fileupload-cancel ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only"><span class="ui-button-icon-left ui-icon ui-icon ui-icon-close"></span><span class="ui-button-text">ui-button</span></button></td>')
                            
                            .appendTo($this.filesTbody);

                    //preview
                    if ($this.isCanvasSupported() && window.File && window.FileReader && $this.IMAGE_TYPES.test(file.name)) {
                        var imageCanvas = $('<canvas></canvas')
                                .appendTo(row.children('td.ui-fileupload-preview')),
                                context = imageCanvas.get(0).getContext('2d'),
                                winURL = window.URL || window.webkitURL,
                                url = winURL.createObjectURL(file),
                                img = new Image();

                        img.onload = function() {
                            var imgWidth = null, imgHeight = null, scale = 1;

                            if ($this.cfg.previewWidth > this.width) {
                                imgWidth = this.width;
                            }
                            else {
                                imgWidth = $this.cfg.previewWidth;
                                scale = $this.cfg.previewWidth / this.width;
                            }

                            var imgHeight = parseInt(this.height * scale);

                            imageCanvas.attr({width: imgWidth, height: imgHeight});
                            context.drawImage(img, 0, 0, imgWidth, imgHeight);
                        }

                        img.src = url;
                    }

                    //progress
                    row.children('td.ui-fileupload-progress').append('<div class="ui-progressbar ui-widget ui-widget-content ui-corner-all" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="ui-progressbar-value ui-widget-header ui-corner-left" style="display: none; width: 0%;"></div></div>');

                    file.row = row;

                    file.row.data('filedata', data);
                    $this.files.push(file);

                    if ($this.cfg.auto) {
                        $this.upload();
                    }
                }
            }
        }});



}