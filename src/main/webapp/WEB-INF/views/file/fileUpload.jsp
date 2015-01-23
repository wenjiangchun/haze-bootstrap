<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/resources/impTagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>上传文件</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/jquery/plupload/js/jquery.plupload.queue/css/jquery.plupload.queue.css" />
<script type="text/javascript" src="${ctx}/resources/jquery/plupload/js/plupload.full.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/jquery/plupload/js/jquery.plupload.queue/jquery.plupload.queue.js"></script>
<script type="text/javascript" src="${ctx}/resources/jquery/plupload/js/i18n/zh_CN.js"></script>
</head>
<body>
<div>
    <form:form id="inputForm" modelAttribute="fileEntity" method="post" enctype="multipart/form-data">
        <div id="uploader">
            <p>您的浏览器不支持  Flash,Gears, BrowserPlus 或  HTML5.</p>
        </div>
    </form:form>
</div>
<script type="text/javascript">
    $(function() {
        $("#uploader").pluploadQueue({
            runtimes : 'html4',
            url : "${ctx}/file/uploadFile",
            max_file_size : '200mb',
            chunk_size : '10mb',
            resize : {
                width : 200,
                height : 200,
                quality : 90,
                crop: true // crop to exact dimensions
            },
            filters : [
                {title : "Image files", extensions : "jpg,gif,png,doc"},
                {title : "Zip files", extensions : "zip,avi"},
                {title : "pdf files", extensions : "pdf,mkv"}
            ],
            rename: true,
            sortable: true,
            dragdrop: true,
            views: {
                list: true,
                thumbs: true, // Show thumbs
                active: 'thumbs'
            },
            flash_swf_url : '${ctx}/resources/jquery/jquery-plupload/js/Moxie.swf',
            silverlight_xap_url : '${ctx}/resources/jquery/jquery-plupload/js/Moxie.xap',
            init : {
            	FileUploaded : function(up, file,info) {
					plupload.each(info,function(arg) {
						alert(info);
										var row = "";
										plupload.each(arg,function(value,key) {
															if (typeof (value) != "function") {
																row += value;
															}
															// 
														});
										file.id = row;
										// top.
									});
					submit();
				}
            }
        });

        /*
         * // Client side form validation
         * $('form').submit(function(e) { var uploader =
         * $('#uploader').pluploadQueue(); // Validate number of
         * uploaded files if (uploader.total.uploaded == 0) { //
         * Files in queue upload them first if
         * (uploader.files.length > 0) { // When all files are
         * uploaded submit form uploader.bind('UploadProgress',
         * function() { if (uploader.total.uploaded ==
         * uploader.files.length) $('form').submit(); });
         * uploader.start(); } else alert('You must at least upload
         * one file.'); e.preventDefault(); } });
         */
        /*
         * var uploader = $('#uploader').plupload('getUploader');
         * uploader.bind('FileUploaded', function(up,file,info) {
         * alert(444); alert(info.toString()); });
         */

        //$("#done_btn").click(function(e) {
        //	var uploader = $('#uploader').plupload('getUploader');
        //alert(uploader.files.length);
        /*
         * if (uploader.total.uploaded == 0) { // Files in queue
         * upload them first if (uploader.files.length > 0) { //
         * When all files are uploaded submit form
         * uploader.bind('UploadProgress', function() { if
         * (uploader.total.uploaded == uploader.files.length) //
         * $('form').submit(); alert(uploader.files); });
         * uploader.start(); } else alert('You must at least
         * upload one file.'); e.preventDefault(); }
         */
        //});



    });

    function submit() {
        alert(777);
        var uploader = $('#uploader').pluploadQueue();
        if (uploader.total.uploaded == uploader.files.length) {
            alert(uploader.files.id);
        }
    }

</script>
</body>
</html>
