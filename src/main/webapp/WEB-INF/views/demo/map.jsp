<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>地图演示</title>
    <style>
        #viewDiv {
            padding: 0;
            margin: 0;
            height: 600px;
            width: 100%;
        }
    </style>

    <link href="http://121.42.151.97:8080/arcgis_js_api/library/4.3/4.3/esri/css/main.css" rel="stylesheet" type="text/css">
    <script src="http://121.42.151.97:8080/arcgis_js_api/library/4.3/4.3/init.js"></script>

    <script>
        require([
            "esri/config",
            "esri/layers/MapImageLayer",
            "esri/Map",
            "esri/Basemap",
            "esri/widgets/BasemapToggle",
            "esri/views/MapView",
            "dojo/domReady!"
        ], function(esriConfig, MapImageLayer, Map,
                    Basemap, BasemapToggle, MapView
        ) {
            var mapBaseLayer = new MapImageLayer({
                url: "http://121.42.151.97:6080/arcgis/rest/services/dangyang/MapServer"
            });

            // Create a Basemap with the WebTileLayer. The thumbnailUrl will be used for
            // the image in the BasemapToggle widget.
            var stamen = new Basemap({
                baseLayers: [mapBaseLayer],
                title: "Terrain",
                id: "terrain"
            });

            var map = new Map({
                basemap: stamen,
                ground: "world-elevation"
            });

            var view = new MapView({
                container: "viewDiv",
                map: map
            });

            //更换底图
            view.then(function() {
                // Add a basemap toggle widget to toggle between basemaps
                var toggle = new BasemapToggle({
                    titleVisible: true,
                    view: view,
                    nextBasemap: stamen
                });

                // Add widget to the top right corner of the view
                view.ui.add(toggle, "top-right");
            });
        });
    </script>
</head>
<body>
<div class="breadcrumbs" id="breadcrumbs">
    <script type="text/javascript">
        try {
            ace.settings.check('breadcrumbs', 'fixed')
        } catch (e) {
        }
    </script>
    <ul class="breadcrumb">
        <li><i class="icon-home home-icon"></i> <a href="#">主页</a></li>
        <li><a href="#">Demo演示</a></li>
        <li class="active">二维码</li>
    </ul>
    <!-- .breadcrumb -->
</div>
<div class="page-content">
    <div id="viewDiv"></div>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        initMenu("viewMap_Menu");
    });
</script>
</body>
</html>
