// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See https://js.arcgis.com/4.3/esri/copyright.txt for details.
//>>built
define("../../../core/declare ../../../core/Accessor ../../../core/arrayUtils ../../../core/Logger ../../../core/watchUtils ../../../core/CollectionFlattener dojo/Evented dojo/on ../lib/glMatrix ../support/projectionUtils ../support/ResourceController ../support/PromiseLightweight ../../../geometry/support/webMercatorUtils ./TerrainRenderer ./OverlayManager ./SurfaceExtentHelper ./SurfaceTilingSchemeLogic ./terrainUtils ./tileUtils ./TerrainConst ./TileGeometryFactory ./SphericalTile ./PlanarTile ./TilemapOnlyTile ./MapTileAgent ./ElevationTileAgent ../support/PreallocArray ../../../core/ObjectPool ../support/aaBoundingRect ../support/mathUtils".split(" "),
function(Q,R,B,S,T,U,V,y,E,z,W,F,J,X,Y,K,Z,q,p,k,aa,ba,ca,da,ea,fa,ga,ha,r,ia){function L(a,b){return a[0]===b[0]&&a[1]===b[1]&&a[2]===b[2]}var M=E.vec3d,G=E.vec4d,N=E.mat4d,C=q.weakAssert,t=S.getLogger("esri.views.3d.terrain"),H=k.MAX_ROOT_TILES,ja=80/180*Math.PI,h=k.TileUpdateTypes,l=G.create(),D=[0,0,0],A=Array(10),O=[null],I={spatialReference:null,tile:null},v={spatialReference:null,extent:null},n={spatialReference:null,extent:null,scale:0},ka=[-Infinity,-Infinity,Infinity,Infinity],P=function(a){a.remove()},
w=Q([R,V],{properties:{ready:{readOnly:!0,get:function(){return!!this.rootTiles}},rootTiles:{readOnly:!0},manifold:{readOnly:!0},tilingScheme:{readOnly:!0},tilingSchemeLogic:{readOnly:!0},tilingSchemeLocked:{readOnly:!0,aliasOf:"tilingSchemeLogic.tilingSchemeLocked"},spatialReference:{readOnly:!0,dependsOn:["tilingScheme"],aliasOf:"tilingScheme.spatialReference"},overlayManager:{value:null},wireframe:{value:!1},opacity:1,cullBackFaces:!1,renderOrder:1,skirts:!0,frontMostTransparent:!1,tileBackground:"data:image/png;base64,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",
velvetOverground:!0,extent:{},loaded:!1},defaultTileBackground:"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAQAAAAEACAIAAADTED8xAAAACXBIWXMAAAsTAAALEwEAmpwYAAAKT2lDQ1BQaG90b3Nob3AgSUNDIHByb2ZpbGUAAHjanVNnVFPpFj333vRCS4iAlEtvUhUIIFJCi4AUkSYqIQkQSoghodkVUcERRUUEG8igiAOOjoCMFVEsDIoK2AfkIaKOg6OIisr74Xuja9a89+bN/rXXPues852zzwfACAyWSDNRNYAMqUIeEeCDx8TG4eQuQIEKJHAAEAizZCFz/SMBAPh+PDwrIsAHvgABeNMLCADATZvAMByH/w/qQplcAYCEAcB0kThLCIAUAEB6jkKmAEBGAYCdmCZTAKAEAGDLY2LjAFAtAGAnf+bTAICd+Jl7AQBblCEVAaCRACATZYhEAGg7AKzPVopFAFgwABRmS8Q5ANgtADBJV2ZIALC3AMDOEAuyAAgMADBRiIUpAAR7AGDIIyN4AISZABRG8lc88SuuEOcqAAB4mbI8uSQ5RYFbCC1xB1dXLh4ozkkXKxQ2YQJhmkAuwnmZGTKBNA/g88wAAKCRFRHgg/P9eM4Ors7ONo62Dl8t6r8G/yJiYuP+5c+rcEAAAOF0ftH+LC+zGoA7BoBt/qIl7gRoXgugdfeLZrIPQLUAoOnaV/Nw+H48PEWhkLnZ2eXk5NhKxEJbYcpXff5nwl/AV/1s+X48/Pf14L7iJIEyXYFHBPjgwsz0TKUcz5IJhGLc5o9H/LcL//wd0yLESWK5WCoU41EScY5EmozzMqUiiUKSKcUl0v9k4t8s+wM+3zUAsGo+AXuRLahdYwP2SycQWHTA4vcAAPK7b8HUKAgDgGiD4c93/+8//UegJQCAZkmScQAAXkQkLlTKsz/HCAAARKCBKrBBG/TBGCzABhzBBdzBC/xgNoRCJMTCQhBCCmSAHHJgKayCQiiGzbAdKmAv1EAdNMBRaIaTcA4uwlW4Dj1wD/phCJ7BKLyBCQRByAgTYSHaiAFiilgjjggXmYX4IcFIBBKLJCDJiBRRIkuRNUgxUopUIFVIHfI9cgI5h1xGupE7yAAygvyGvEcxlIGyUT3UDLVDuag3GoRGogvQZHQxmo8WoJvQcrQaPYw2oefQq2gP2o8+Q8cwwOgYBzPEbDAuxsNCsTgsCZNjy7EirAyrxhqwVqwDu4n1Y8+xdwQSgUXACTYEd0IgYR5BSFhMWE7YSKggHCQ0EdoJNwkDhFHCJyKTqEu0JroR+cQYYjIxh1hILCPWEo8TLxB7iEPENyQSiUMyJ7mQAkmxpFTSEtJG0m5SI+ksqZs0SBojk8naZGuyBzmULCAryIXkneTD5DPkG+Qh8lsKnWJAcaT4U+IoUspqShnlEOU05QZlmDJBVaOaUt2ooVQRNY9aQq2htlKvUYeoEzR1mjnNgxZJS6WtopXTGmgXaPdpr+h0uhHdlR5Ol9BX0svpR+iX6AP0dwwNhhWDx4hnKBmbGAcYZxl3GK+YTKYZ04sZx1QwNzHrmOeZD5lvVVgqtip8FZHKCpVKlSaVGyovVKmqpqreqgtV81XLVI+pXlN9rkZVM1PjqQnUlqtVqp1Q61MbU2epO6iHqmeob1Q/pH5Z/YkGWcNMw09DpFGgsV/jvMYgC2MZs3gsIWsNq4Z1gTXEJrHN2Xx2KruY/R27iz2qqaE5QzNKM1ezUvOUZj8H45hx+Jx0TgnnKKeX836K3hTvKeIpG6Y0TLkxZVxrqpaXllirSKtRq0frvTau7aedpr1Fu1n7gQ5Bx0onXCdHZ4/OBZ3nU9lT3acKpxZNPTr1ri6qa6UbobtEd79up+6Ynr5egJ5Mb6feeb3n+hx9L/1U/W36p/VHDFgGswwkBtsMzhg8xTVxbzwdL8fb8VFDXcNAQ6VhlWGX4YSRudE8o9VGjUYPjGnGXOMk423GbcajJgYmISZLTepN7ppSTbmmKaY7TDtMx83MzaLN1pk1mz0x1zLnm+eb15vft2BaeFostqi2uGVJsuRaplnutrxuhVo5WaVYVVpds0atna0l1rutu6cRp7lOk06rntZnw7Dxtsm2qbcZsOXYBtuutm22fWFnYhdnt8Wuw+6TvZN9un2N/T0HDYfZDqsdWh1+c7RyFDpWOt6azpzuP33F9JbpL2dYzxDP2DPjthPLKcRpnVOb00dnF2e5c4PziIuJS4LLLpc+Lpsbxt3IveRKdPVxXeF60vWdm7Obwu2o26/uNu5p7ofcn8w0nymeWTNz0MPIQ+BR5dE/C5+VMGvfrH5PQ0+BZ7XnIy9jL5FXrdewt6V3qvdh7xc+9j5yn+M+4zw33jLeWV/MN8C3yLfLT8Nvnl+F30N/I/9k/3r/0QCngCUBZwOJgUGBWwL7+Hp8Ib+OPzrbZfay2e1BjKC5QRVBj4KtguXBrSFoyOyQrSH355jOkc5pDoVQfujW0Adh5mGLw34MJ4WHhVeGP45wiFga0TGXNXfR3ENz30T6RJZE3ptnMU85ry1KNSo+qi5qPNo3ujS6P8YuZlnM1VidWElsSxw5LiquNm5svt/87fOH4p3iC+N7F5gvyF1weaHOwvSFpxapLhIsOpZATIhOOJTwQRAqqBaMJfITdyWOCnnCHcJnIi/RNtGI2ENcKh5O8kgqTXqS7JG8NXkkxTOlLOW5hCepkLxMDUzdmzqeFpp2IG0yPTq9MYOSkZBxQqohTZO2Z+pn5mZ2y6xlhbL+xW6Lty8elQfJa7OQrAVZLQq2QqboVFoo1yoHsmdlV2a/zYnKOZarnivN7cyzytuQN5zvn//tEsIS4ZK2pYZLVy0dWOa9rGo5sjxxedsK4xUFK4ZWBqw8uIq2Km3VT6vtV5eufr0mek1rgV7ByoLBtQFr6wtVCuWFfevc1+1dT1gvWd+1YfqGnRs+FYmKrhTbF5cVf9go3HjlG4dvyr+Z3JS0qavEuWTPZtJm6ebeLZ5bDpaql+aXDm4N2dq0Dd9WtO319kXbL5fNKNu7g7ZDuaO/PLi8ZafJzs07P1SkVPRU+lQ27tLdtWHX+G7R7ht7vPY07NXbW7z3/T7JvttVAVVN1WbVZftJ+7P3P66Jqun4lvttXa1ObXHtxwPSA/0HIw6217nU1R3SPVRSj9Yr60cOxx++/p3vdy0NNg1VjZzG4iNwRHnk6fcJ3/ceDTradox7rOEH0x92HWcdL2pCmvKaRptTmvtbYlu6T8w+0dbq3nr8R9sfD5w0PFl5SvNUyWna6YLTk2fyz4ydlZ19fi753GDborZ752PO32oPb++6EHTh0kX/i+c7vDvOXPK4dPKy2+UTV7hXmq86X23qdOo8/pPTT8e7nLuarrlca7nuer21e2b36RueN87d9L158Rb/1tWeOT3dvfN6b/fF9/XfFt1+cif9zsu72Xcn7q28T7xf9EDtQdlD3YfVP1v+3Njv3H9qwHeg89HcR/cGhYPP/pH1jw9DBY+Zj8uGDYbrnjg+OTniP3L96fynQ89kzyaeF/6i/suuFxYvfvjV69fO0ZjRoZfyl5O/bXyl/erA6xmv28bCxh6+yXgzMV70VvvtwXfcdx3vo98PT+R8IH8o/2j5sfVT0Kf7kxmTk/8EA5jz/GMzLdsAAAAgY0hSTQAAeiUAAICDAAD5/wAAgOkAAHUwAADqYAAAOpgAABdvkl/FRgAAA2JJREFUeNrs3d1O20AQgFFvRJInQLQBhHj/h0JVW34El1yQ2F73DVq3jTys55zrqUBbPrErZUSZ+vcOsto4AjK76Lqu1vr8+G3mPzjc3D/+eJj/Bcz/cd75R80fbu79BsAVCAQAAgABgABAACAAEAAIAAQAAgABQPOKfQAy83Ho+HnnHzXv49B4A4AAQAAgABAACAAEAAIAAYAAQAAgABAANM4+AKnZB4ifd/5R8/YB8AYAAYAAQAAgABAACAAEAAIAAYAAQAAgAGicfQBSsw8QP+/8o+btA+ANAAIAAYAAQAAgABAACAAEAAIAAYAAQADQOPsApGYfIH7e+UfN2wfAGwAEAAIAAYAAQAAgABAACAAEAAIAAXA201QdggAggH0AUrMPED8/jsPL03fns/y8fQC8AUAAIAAQAAgABAACAAGAAEAAIAAQAAgAGmcfgNTsA8TP2weImrcPgDcACAAEAAIAAYAAQAAgABAACAAEAAIAAUDj7AOQmn2A+Hn7AFHz9gHwBgABgABAACAAEAAIAAQAAgABgABgNS4cAf9pu9u3O1+m/n2aplKK/0j+TX86/tVP5+eZ3+729gFIfwWyDxA7bx8gat4+ANkJAAGAAEAAIAAQAAgABAACAAGAAEAAIABonn0AUrMPED9vHyBq3j4A3gAgABAACAAEAAIAAYAAQAAgABAA51VrdQgCAAHAsuwDkJp9gPj5vj+9vvx0PsvP2wfAGwAEAAIAAYAAQAAgABAACAAEAAIAAYAAoHH2AUjNPkD8vH2AqHn7AHgDgABAACAAEAAIAAQAAgABgABAACAAEAA0zj4AqdkHiJ+3DxA1bx8AbwAQACQ0DL0AyKuOowBwBYKUSikCIHUBAsAVCAQAAgABgABAALBy9gFIzT5A/Lx9gKj5y6trVyC8AUAAIAAQAAgAVq90Pg5N5gA2AsAVCAQAAgABgABAALB29gFIzT5A/Lx9gKj5q6+3rkB4A4AAQAAgABAACADWzB/IIHsCAsAVCARAlKlWhyAAEAAIABZjH4DU7APEz5+OH2+vT85n+fkvhztXILwBQAAgABAACAAEAGtWigBIHcBGALgCgQBAACAAyPMO9nHosxuHodZx5vB2t691HIdh/nx/Os7/Zsz/fvgXAAAA//8DAF1P1hM2ICMfAAAAAElFTkSuQmCC",
constructor:function(){this._set("rootTiles",null);this._iteratorPool=new ha(p.IteratorPreorder);this._postorderIterator=new p.IteratorPostorder;this._topLevelTilemapOnlyTiles=Array(k.TILEMAP_SIZE_EXP+1);for(a=0;a<this._topLevelTilemapOnlyTiles.length;a++)this._topLevelTilemapOnlyTiles[a]=new da([a-k.TILEMAP_SIZE_EXP,0,0]);this._dataExtent=this._clippingExtent=null;this._rootExtent=[0,0,0,0];this.notifyChange("extent");this._elevationBounds=[0,0];this._frameUpdateLowerPrio=new ga(500);this.maxTextureScale=
1.2;this._stage=this._view._stage;this._updateNextFrame=this._lvPendingUpdates=this._pendingUpdates=this.suspended=this.visible=!1;this._curOverlayOpacity=1;this._curEyePos=M.create();this._curSplitLimits=[0,0,0,0,0,0];this._curFrustumPlanes=Array(6);this._viewProjectionMatrix=N.identity();for(var a=0;6>a;++a)this._curFrustumPlanes[a]=G.create();this.tilemapStats={tilemapRequestsSent:0,tilemapRequestsPending:0,tilemapRequestErrors:0,fullTilemaps:0,emptyTilemaps:0,tilesRequested:0,tileRequestsSent:0,
tileRequestErrors:0,tilesNotPresent:0};this._layerViews=[[],[]];this._layerIndexByLayerViewId=[{},{}];this._basemapLayerViewHandles={};this._groupLayerViewHandles={};this._layerViewChangesHandle=null;this.hideSkirtsDistanceFromExtentMargin=1.2;this.hideSkirtsMinimumCameraTilt=ja},normalizeCtorArgs:function(a,b){this._view=a;this._set("manifold",b);this.TileClass="planar"===b?ca:ba;return{}},initialize:function(){this._renderer=new X(this.manifold);this._renderer.loaded=this._setLoaded.bind(this);
this._renderer.updateTileBackground(this.tileBackground);this._renderer.install(this._view._stage);this.overlayManager=new Y(this,this._view);this.extentHelper=new ("spherical"===this.manifold?K.SurfaceExtentHelperGlobal:K.SurfaceExtentHelperLocal)({layers:this._view.map.allLayers,layerViews:this._view.allLayerViews,spatialReference:this._view.spatialReference});var a;a=this._view.defaultsFromMap?new U({root:this._view.map,rootCollectionNames:this._view.defaultsFromMap.mapCollectionPaths,getChildrenFunction:function(a){return a.layers}}):
this._view.map.allLayers;this._set("tilingSchemeLogic",new Z({layers:a,extentHelper:this.extentHelper,manifold:this.manifold,viewSpatialReference:this._view.spatialReference}));this.tilingSchemeLogic.watch("tilingScheme",this._updateTilingSchemeAndExtent.bind(this),!0);this.tilingSchemeLogic.watch("extent",this._updateTilingSchemeAndExtent.bind(this),!0);this._updateTilingSchemeAndExtent();this._streamDataSupplier=this._view.resourceController.registerClient(this,W.ClientType.TERRAIN);this._idleWorkers=
{needsUpdate:this._needsIdleUpdate,idleFrame:this._idleUpdate};this._view.resourceController.registerFrameWorker(this._frameUpdate.bind(this));this._view.resourceController.registerIdleFrameWorker(this,this._idleWorkers);this._viewChangeListenerHandles=[];this._viewChangeUpdate=this._viewChangeUpdate.bind(this);this._viewChangeListenerHandles.push(this._view.on("resize",this._viewChangeUpdate));this._viewChangeListenerHandles.push(T.on(this._view,"navigation","currentViewChanged",this._viewChangeUpdate));
this._viewChangeListenerHandles.push(this._view.watch("qualitySettings.tiledSurface.lodBias",this._viewChangeUpdate));this._layerViewChangesHandle=this._view.allLayerViews.on("change",this._handleLayerViewChanges.bind(this));this._handleLayerViewChanges({added:this._view.allLayerViews.toArray(),removed:[],moved:[]});this._clippingChangedHandle=this._view.watch("clippingArea",this._clippingChanged.bind(this));this._updateClippingExtent()},destroy:function(){this._removeAllTiles();this.tilingSchemeLogic.destroy();
this._set("tilingSchemeLogic",null);this.extentHelper.destroy();this.extentHelper=null;for(var a in this._basemapLayerViewHandles)this._unregisterTiledLayerView(a);this._view.resourceController.deregisterFrameWorker(this);this._view.resourceController.deregisterIdleFrameWorker(this);this._view.resourceController.deregisterClient(this);this._viewChangeListenerHandles.forEach(P);for(var b in this._groupLayerViewHandles)this._groupLayerViewHandles[b].forEach(P);this._layerViewChangesHandle.remove();
this._clippingChangedHandle.remove();this.overlayManager&&(this.overlayManager.destroy(),this.overlayManager=null);this._renderer.uninstall(this._stage);this._streamDataSupplier=this._stage=this._view=this._renderer=null},setVisibility:function(a){a!==this.visible&&(this.visible=a,this._renderer.setVisibility(a),this.setUpdatesDisabled(!a),a&&this._viewChangeUpdate())},isVisible:function(){return this.visible&&this.ready},setUpdatesDisabled:function(a){(this.suspended=a)||this._viewChangeUpdate()},
getElevation:function(a){return this.ready&&0!==this.rootTiles[0].layerInfo[k.LayerClass.ELEVATION].length?Array.isArray(a)?this._getElevation(a):z.pointToVector(a,l,this.tilingScheme.spatialReference)?this._getElevation(l):(t.error("TerrainSurface.getElevation(): could not project given point to tiling scheme coordinate system"),null):null},_getElevation:function(a){var b=k.LayerClass.ELEVATION,c=this.rootTiles[0].layerInfo[b].length,d;A.length<c&&(A.length=c);for(var f=0;f<this.rootTiles.length;f++){var e=
this.rootTiles[f];if(p.isPosWithinTile(e,a))for(;e;){var g=e.layerInfo[b];for(d=0;d<c;d++)g[d].data&&(A[d]=g[d].data.samplerData);if(!e.children[0])break;d=0;a[0]>e.children[0].extent[2]&&(d+=1);a[1]<e.children[0].extent[1]&&(d+=2);e=e.children[d]}}a=aa.elevationSampler(a[0],a[1],A);for(d=0;d<c;d++)A[d]=void 0;return a},getElevationBounds:function(){return this._elevationBounds},getScale:function(a){if(this.tilingScheme){if(!z.pointToVector(a,l,this.spatialReference))return t.error("TerrainSurface.getElevation(): could not project given point to tiling scheme coordinate system"),
null;if(this.rootTiles)for(var b=0;b<this.rootTiles.length;b++)if(a=this.rootTiles[b],p.isPosWithinTile(a,l)){for(;a.children[0];)b=0,l[0]>a.children[0].extent[2]&&(b+=1),l[1]<a.children[0].extent[1]&&(b+=2),a=a.children[b];return this._getLodBiasCorrectedScale(a.lij[0])}}return 1E100},queryVisibleScaleRange:function(a,b,c,d){b=b?this.tilingScheme.levelAtScale(b):0;c=c?this.tilingScheme.levelAtScale(c):Infinity;var f=this._getLodBias();this._renderer.queryVisibleLevelRange(a,b+f,c+f,d)},_setLoaded:function(){this.loaded||
(this.loaded=!0,this.emit("load"))},_updateTilingSchemeAndExtent:function(){var a=this.tilingSchemeLogic.extent,b=this.tilingSchemeLogic.tilingScheme,c=!1;a&&!r.equals(a,this._dataExtent)&&(c=!0,this._dataExtent?r.set(this._dataExtent,a):this._dataExtent=r.create(a));b!=this.tilingScheme&&(C(b,"tiling scheme cannot be reset to undefined"),c=!0,this.tilingScheme&&this._removeAllTiles(),this._set("tilingScheme",b),this._updateClippingExtent(),b&&(this._updateTiledLayers(),this._renderer.setTileSize(b.pixelSize[0]),
this.overlayManager.setSpatialReference(b.spatialReference,"spherical"===this.manifold)));c&&this._updateRootTiles()},_acquireTile:function(a,b,c,d){var f=this.TileClass.Pool.acquire();D[0]=a;D[1]=b;D[2]=c;f.init(D,d,this,this.tilingScheme);return f},_releaseTile:function(a){a.dispose();a.parent=null;a.parentSurface=null;this.TileClass.Pool.release(a)},_updateRootTiles:function(){var a=this._clippingExtent||this._dataExtent,b=this.tilingScheme;if(a&&b){var c=b.rootTilesInExtent(a,l,Infinity);if(this.rootTiles){if(c.length>
H){t.warn(w.TOO_MANY_ROOT_TILES_AFTER_CHANGE);return}var a=this.rootTiles.map(function(a){return a.lij}),d=B.difference(a,c,L);if(0<d.removed.length||0<d.added.length){var f=this.rootTiles.filter(function(a){return-1<B.findIndex(d.removed,L.bind(null,a.lij))?(this._purgeChildTiles(a),this._purgeTile(a),!1):!0}.bind(this));d.added.forEach(function(a){a=this._acquireTile(0,a[1],a[2],null);f.push(a);this._loadTile(a)}.bind(this));this._set("rootTiles",f);this._renderer.setRootTiles(this.rootTiles)}}else c.length>
H&&(t.warn(w.TOO_MANY_ROOT_TILES_FOR_LAYER_ERROR),c=b.rootTilesInExtent(a,l,H)),this._set("rootTiles",c.map(function(a){a=this._acquireTile(0,a[1],a[2],null);this._loadTile(a);return a}.bind(this))),this._renderer.setRootTiles(this.rootTiles);B.equals(l,this._rootExtent)||(this._rootExtent=G.create(l),this._hasFixedExtent()||this.notifyChange("extent"));this.setVisibility(!0);this._viewChangeUpdate();this.notifyChange("ready")}},_viewChangeUpdate:function(){this._stage&&!this.suspended&&this.tilingScheme&&
this.visible&&(this._updateViewDependentParameters(),this._updateOverlayOpacity(this._curEyePos),this._updateTiles(this.rootTiles),this.overlayManager&&this.overlayManager.setOverlayDirty())},_hasVisibleSiblings:function(a){Array.isArray(a)||(O[0]=a,a=O);for(var b=0;b<a.length;b++){var c=a[b],d=c.parent;if(d)for(var f=0;4>f;f++){var e=d.children[f];if(e&&e!==c&&e.visible)return!0}}return!1},_updateTiles:function(a){var b=this._iteratorPool.acquire();b.reset(a);var c=this._curSplitLimits,d=this._curFrustumPlanes,
f=this._curEyePos,e;this._hasVisibleSiblings(a)?(a=this._elevationBounds[0],e=this._elevationBounds[1]):(a=Infinity,e=-Infinity);for(;!b.done;){var g=b.next();g.updateClippingStatus(this._clippingExtent);var m=!0;if(g.updateVisibility(d,f)){g.updateScreenDepth(this._viewProjectionMatrix);g.renderData&&(a=Math.min(g.elevationBounds[0],a),e=Math.max(g.elevationBounds[1],e));var x=g.shouldSplit(c,f);x===h.SPLIT?(g.pendingUpdates&=~h.MERGE,g.renderData?(m=!1,g.pendingUpdates|=h.SPLIT,b.skip()):m=!1):
(g.pendingUpdates&=~h.SPLIT,x===h.VSPLITMERGE&&g.updateAgents(k.LayerClass.ELEVATION),b.skip())}else b.skip();if(m&&!g.renderData){g.pendingUpdates|=h.MERGE;g.pendingUpdates&=~h.SPLIT;m=this._iteratorPool.acquire();for(m.reset(g);!m.done;)x=m.next(),x.updateVisibility(d,f),x.visible&&x.updateScreenDepth(this._viewProjectionMatrix);this._iteratorPool.release(m)}0!==g.pendingUpdates&&(this._pendingUpdates=!0)}this._iteratorPool.release(b);isFinite(a)&&isFinite(e)&&(this._elevationBounds[0]!==a||this._elevationBounds[1]!==
e)&&(this._elevationBounds[0]=a,this._elevationBounds[1]=e,y.emit(this,"elevation-bounds-change"))},_viewParamSelector:{projectionMatrix:!0,fovX:!0,viewport:!0},_updateViewDependentParameters:function(){var a=this._view.navigation.currentCamera,b=Math.tan(.5*a.fovX),c=Math.tan(.5*a.fovY),d=this.tilingScheme.pixelSize,f=Math.pow(2,-this._getLodBias());this._curSplitLimits[0]=b;this._curSplitLimits[1]=d[0]/a.width*this.maxTextureScale*f;this._curSplitLimits[2]=c;this._curSplitLimits[3]=d[1]/a.height*
this.maxTextureScale*f;this._curSplitLimits[4]=this.tilingScheme.getMaxLod();this._curSplitLimits[5]=this._view.qualitySettings.tiledSurface.angledSplitBias;a.copyFrustumPlanes(this._curFrustumPlanes);N.multiply(a.projectionMatrix,a.viewMatrix,this._viewProjectionMatrix);M.set(a.eye,this._curEyePos);q.autoUpdateSkirtsVisibility(this,this._curEyePos)},_setLayerViewsUpdating:function(){for(var a=0;a<k.LayerClass.LAYER_CLASS_COUNT;a++)for(var b=this._layerViews[a],c=0;c<b.length;c++)b[c]._evaluateUpdatingState(this._pendingUpdates)},
_frameUpdateTraversal:function(a){if(!this.suspended){this._frameUpdateLowerPrio.clear();var b=this._renderer.resourceCounter.numTileTexturesComposited,c=this._iteratorPool.acquire();c.reset(this.rootTiles);for(var d=!1,f=!1;!c.done&&(1<a.remaining()||!d)&&12>this._renderer.resourceCounter.numTileTexturesComposited-b;){var e=c.next();e.pendingUpdates&h.MERGE?(this._mergeTile(e),e.pendingUpdates&=~h.MERGE,d=!0,c.skip()):e.pendingUpdates&h.SPLIT?(this._splitTile(e),e.pendingUpdates&=~h.SPLIT,d=!0,c.skip()):
0<e.pendingUpdates&&this._frameUpdateLowerPrio.push(e);0!==e.pendingUpdates&&(f=!0)}this._pendingUpdates=f||!c.done;this._iteratorPool.release(c);return d}},_updateTileGeometry:function(a){this._renderer._updateTileGeometry(a);I.spatialReference=this.spatialReference;I.tile=a;y.emit(this,"elevation-change-tile",I)},_updateTileTexture:function(a){this._renderer.updateTileTexture(a)},_frameUpdate:function(a){if(this.rootTiles){for(var b=this._frameUpdateTraversal(a);(1<a.remaining()||!b)&&0<this._frameUpdateLowerPrio.length;){var c=
this._frameUpdateLowerPrio.pop();c.pendingUpdates&h.DECODE_LERC?(this._decodeLERC(c),c.pendingUpdates&=~h.DECODE_LERC,b=!0):c.pendingUpdates&h.UPDATE_GEOMETRY?(this._renderer.updateTileGeometryNeedsUpdate(c),this._updateTileGeometry(c),b=!0,c.pendingUpdates&=~h.UPDATE_GEOMETRY):c.pendingUpdates&h.UPDATE_TEXTURE&&(this._updateTileTexture(c),c.pendingUpdates&=~h.UPDATE_TEXTURE,b=!0);0!==c.pendingUpdates&&(this._pendingUpdates=!0)}0<this._frameUpdateLowerPrio.length&&(this._pendingUpdates=!0);this._streamDataSupplier._loader.hasPendingDownloads()&&
(this._pendingUpdates=!0);this._pendingUpdates===this._lvPendingUpdates||!this._pendingUpdates&&20!==++this._updateNextFrame||(this._setLayerViewsUpdating(),this._lvPendingUpdates=this._pendingUpdates,this._updateNextFrame=0)}},_needsIdleUpdate:function(){return this.isVisible()&&this.overlayManager&&this.overlayManager.overlaysNeedUpdate()},_idleUpdate:function(a){this.overlayManager.updateOverlay();this._updateOverlayOpacity(this._curEyePos)},_updateClippingExtent:function(){if(!this.spatialReference)return!1;
var a=[],b=null;z.extentToBoundingRect(this._view.clippingArea,a,this.spatialReference)&&(b=a);if(B.equals(b,this._clippingExtent))return!1;this._clippingExtent=b;this._renderer.clippingExtent=b;this.notifyChange("extent");return!0},_clippingChanged:function(){this._updateClippingExtent()&&this._updateRootTiles()},_getLodBias:function(){return Math.round(this._view.qualitySettings.tiledSurface.lodBias)},_getLodBiasCorrectedScale:function(a){var b=this.tilingScheme.levels;a=ia.clamp(a-this._getLodBias(),
0,b.length-1);return b[a].scale},_cancelTilemapRequests:function(a){for(var b=0;b<k.LayerClass.LAYER_CLASS_COUNT;b++){var c=a.layerInfo[b];if(c)for(var d=0;d<c.length;d++){var f=c[d];f.tilemapRequest&&(f.tilemapRequest.cancel(),f.tilemapRequest=null)}}},_removeAllTiles:function(){this.rootTiles&&(this.rootTiles.forEach(function(a){this._purgeChildTiles(a);this._purgeTile(a)}.bind(this)),this._set("rootTiles",null),this.notifyChange("ready"));for(var a=0;a<this._topLevelTilemapOnlyTiles.length;a++)this._cancelTilemapRequests(this._topLevelTilemapOnlyTiles[a]);
this.setVisibility(!1)},_purgeChildTiles:function(a){var b=this._postorderIterator;for(b.reset(a);!b.done;){for(var c=b.next(),d=0;4>d;d++)c.children[d]=null;c!==a&&this._purgeTile(c)}},_purgeTile:function(a){a.unload(this._renderer);this._cancelTilemapRequests(a);a.parent=null;a.releaseLayerResources();this._releaseTile(a)},_splitTile:function(a){var b=a.lij[0]+1,c=2*a.lij[1],d=2*a.lij[2];a.children[0]=this._createTile(b,c,d,a);a.children[1]=this._createTile(b,c,d+1,a);a.children[2]=this._createTile(b,
c+1,d,a);a.children[3]=this._createTile(b,c+1,d+1,a);a.unload(this._renderer);n.spatialReference=this.spatialReference;n.extent=a.extent;n.scale=this._getLodBiasCorrectedScale(b);y.emit(this,"scale-change",n)},_createTile:function(a,b,c,d){C(d,"_createTile sanity check");a=this._acquireTile(a,b,c,d);a.updateClippingStatus(this._clippingExtent);a.updateVisibility(this._curFrustumPlanes,this._curEyePos);a.visible&&(a.updateScreenDepth(this._viewProjectionMatrix),a.shouldSplit(this._curSplitLimits,this._curEyePos)===
h.SPLIT&&(a.pendingUpdates|=h.SPLIT,this._pendingUpdates=!0));this._loadTile(a);return a},_mergeTile:function(a){C(!a.renderData,"_mergeTile sanity check");this._loadTile(a);this._purgeChildTiles(a);n.spatialReference=this.spatialReference;n.extent=a.extent;n.scale=this._getLodBiasCorrectedScale(a.lij[0]);y.emit(this,"scale-change",n)},_loadTile:function(a){a.load(this._renderer);this.overlayManager&&this.overlayManager.hasOverlays()&&this.overlayManager.setOverlayParamsOfTile(a,a.renderData,this._curOverlayOpacity)},
_decodeLERC:function(a){var b=k.LayerClass.ELEVATION,c=a.layerInfo[b];if(c)for(var d=0;d<c.length;d++){var f=c[d];f.pendingUpdates&=~h.DECODE_LERC;if(f.rawData){var e=a.createElevationDataFromLERC(f.rawData);f.rawData=null;if(e){f.data=e;var f=a.lij[0],g=this._iteratorPool.acquire();for(g.reset(a);!g.done;){var m=g.next();m.findElevationBoundsForLayer(d,f);m.computeElevationBounds()}this._iteratorPool.release(g);a.dataArrived(d,b,e);this._updateTiles(a);v.spatialReference=this.spatialReference;v.extent=
a.extent;this._emitElevationChange(v)}}}},_handleLayerViewChanges:function(a){var b=!1;a.added.forEach(function(a){var c=a.layer;if(q.isTiledLayerView(a))this._registerTiledLayer(c,a),c.loaded&&(b=!0);else if(a.supportsDraping&&this.overlayManager)this.overlayManager.registerLayerView(a);else if("IntegratedMeshLayer"===c.operationalLayerType&&!this._renderer.isStencilEnabledLayerExtent(a.uid)){var f=c.fullExtent,c=f.spatialReference,e=[f.xmax,f.ymax,f.zmax],g=Array(6);(f=(f=z.bufferToBuffer([f.xmin,
f.ymin,f.zmin],c,0,g,this.spatialReference,0))&&z.bufferToBuffer(e,c,0,g,this.spatialReference,3))&&this._renderer.addStencilEnabledLayerExtent(a.uid,g)}}.bind(this));a.removed.forEach(function(a){q.isTiledLayerView(a)?(b=!0,this._unregisterTiledLayerView(a.uid)):a.supportsDraping&&this.overlayManager?this.overlayManager.unregisterLayerView(a):this._renderer.isStencilEnabledLayerExtent(a.uid)&&this._renderer.removeStencilEnabledLayerExtent(a.uid)}.bind(this));(b=b||0<a.moved.filter(q.isTiledLayerView).length)&&
this._updateTiledLayers()},_registerTiledLayer:function(a,b){a=[];a.push(b.watch("suspended",function(){this._updateTiledLayers()}.bind(this)));a.push(b.watch("fullOpacity",this._updateTileTextures.bind(this)));b.on("data-changed",function(){var a=q.isElevationLayerView(b)?k.LayerClass.ELEVATION:k.LayerClass.MAP,d=this._layerIndexByLayerViewId[a][b.uid];null!=d&&this._invalidateLayerData(d,a)}.bind(this));this._basemapLayerViewHandles[b.uid]=a},_unregisterTiledLayerView:function(a){var b=this._basemapLayerViewHandles[a];
if(b){for(var c=0;c<b.length;c++)b[c].remove();delete this._basemapLayerViewHandles[a]}},_updateTiledLayers:function(){if(this.tilingScheme){var a=this._view.allLayerViews,b=[[],[]],c=k.LayerClass,d=null,f=r.create(r.NEGATIVE_INFINITY),e=function(a){var e=a.layer;if(e&&a&&!a.suspended&&q.isTiledLayerView(a)){var g=e.fullExtent;if(g){var m=q.isVectorTileLayerView(a)&&256===this.tilingScheme.pixelSize[0]?e.tileInfo256:e.tileInfo;this.tilingScheme.compatibleWith(m)?(r.expand(f,g),q.isElevationLayerView(a)?
b[c.ELEVATION].push(a):(Infinity!==a.maxDataLevel&&(null===d||a.maxDataLevel>d)&&(d=a.maxDataLevel),b[c.MAP].push(a))):t.warn("Terrain: tiling scheme of layer "+e.id+" is incompatible with other tiled layers, will not be drawn")}else t.warn("Terrain: Map or elevation layer does not have fullExtent: "+e.id)}}.bind(this);a.forEach(e,this);for(var g=0;g<c.LAYER_CLASS_COUNT;g++){e=this._layerViews[g];a=b[g];a.reverse();var m=e.length!==a.length,h=a.length,l=Array(h),n=Array(e.length);this._layerIndexByLayerViewId[g]=
{};for(var u=0;u<h;u++){this._layerIndexByLayerViewId[g][a[u].uid]=u;var p=e.indexOf(a[u]);l[u]=p;u!==p&&(m=!0);-1<p&&(n[p]=u)}if(m){this._topLevelTilemapOnlyTiles.forEach(function(a){a.modifyLayers(n,l,g)});e=this._postorderIterator;if(this.rootTiles)for(e.reset(this.rootTiles);!e.done;)e.next().modifyLayers(n,l,g);this._layerViews[g]=a;if(this.rootTiles){for(e.reset(this.rootTiles);!e.done;)a=e.next(),a.restartAgents(g),g===c.ELEVATION&&a.computeElevationBounds();this._updateTiles(this.rootTiles)}g===
c.ELEVATION&&this.tilingScheme&&(v.spatialReference=this.tilingScheme.spatialReference,v.extent=ka,this._emitElevationChange(v))}}this.tilingScheme.levels.length-1<d&&(this.tilingScheme.ensureMaxLod(d),this._viewChangeUpdate())}},_emitElevationChange:function(a){y.emit(this,"elevation-change",a);if(w.UPDATE_OVERLAY_MANAGER_BY_ELEVATION_UPDATES_DEFAULT)this.overlayManager.onElevationChange(a)},_getLayerExtentUnion:function(a){var b=this._view.allLayerViews,c=r.create(r.NEGATIVE_INFINITY);b.forEach(function(b){var d=
b.layer;(b=b.fullExtentInViewSpatialReference||d.fullExtent)&&!b.spatialReference.equals(a)&&(b=J.canProject(b.spatialReference,a)?J.project(b,a):null);b&&r.expand(c,b)});return r.allFinite(c)?c:null},layerViewByIndex:function(a,b){return this._layerViews[b][a]},agentTypeByLayerIndex:function(a,b){return b===k.LayerClass.ELEVATION?fa:ea},numLayers:function(a){return this._layerViews[a].length},numTotalLayers:function(){return this._layerViews.reduce(function(a,b){return b.length+a},0)},_updateTileTextures:function(){var a=
this._iteratorPool.acquire();for(a.reset(this.rootTiles);!a.done;)a.next().updateTexture();this._iteratorPool.release(a)},_invalidateLayerData:function(a,b){var c=this._iteratorPool.acquire();for(c.reset(this.rootTiles);!c.done;)c.next().reinitializeLayer(a,b);this._iteratorPool.release(c)},requestTileData:function(a,b,c){this.tilemapStats.tilesRequested++;var d=this.layerViewByIndex(b,c);if(d.layer.tilemapCache){var f=this.getTilemapTile(a),e=f.layerInfo[c][b];if(e.tilemap){if(!f.tileDataAvailable(a,
b,c))return this.tilemapStats.tilesNotPresent++,this._dispatchDataEvent(a,"dataMissing",c,d,{notInTilemap:!0}),b=new F.Promise,b.reject(),b}else{var g=new F.Promise;g.actualTileRequestPromise=null;e.tilemapRequest||(e.tilemapRequest=this.requestTilemap(f,b,c,d));e.tilemapRequest.then(function(){e.tilemapRequest=null;if(!g.isCancelled()){var b=this._layerIndexByLayerViewId[c][d.uid];null!=b&&(f.tileDataAvailable(a,b,c)?(g.actualTileRequestPromise=this._requestTileData(a,b,c,d),g.actualTileRequestPromise.then(function(){g.resolve()})):
(this.tilemapStats.tilesNotPresent++,this._dispatchDataEvent(a,"dataMissing",c,d,{notInTilemap:!0}),g.reject()))}}.bind(this));return g}}return this._requestTileData(a,b,c,d)},_requestTileData:function(a,b,c,d){this.tilemapStats.tileRequestsSent++;b=d.getTileUrl(a.lij[0],a.lij[1],a.lij[2]);var f=this;if(c===k.LayerClass.ELEVATION)b=this._streamDataSupplier.request(b,"binary"),b.then(function(b,e){var g=f._layerIndexByLayerViewId[c][d.uid];null!=g?(g=a.layerInfo[c][g],e.url=b,g.rawData=e,a.pendingUpdates|=
h.DECODE_LERC,g.pendingUpdates|=h.DECODE_LERC,f._pendingUpdates=!0):t.warn("TerrainSurface: received data from unknown layer %d %s",c,a.lij.toString())},function(b){f.tilemapStats.tileRequestErrors++;f._dispatchDataEvent(a,"dataMissing",c,d,b)});else if(q.isVectorTileLayerView(d)){b=d.tileHandler;var e=d.schemeHelper.getCompatibleLevelRowCol(a.lij);b=b.getVectorTileWithLRC(e[0],e[1],e[2],0);b.then(function(b){f._dispatchDataEvent(a,"dataArrived",c,d,b)}).otherwise(function(b){"cancel"!==b&&(f._dispatchDataEvent(a,
"dataMissing",c,d,b),f.tilemapStats.tileRequestErrors++)})}else b=this._streamDataSupplier.request(b,"image"),b.then(function(b,e){f._dispatchDataEvent(a,"dataArrived",c,d,e)},function(b){f.tilemapStats.tileRequestErrors++;f._dispatchDataEvent(a,"dataMissing",c,d,b)});return b},requestTilemap:function(a,b,c,d){var f,e=new F.Promise(function(){f.cancel()}),g=a.lij[0]+k.TILEMAP_SIZE_EXP,m=a.lij[1]<<k.TILEMAP_SIZE_EXP,h=a.lij[2]<<k.TILEMAP_SIZE_EXP;this.tilemapStats.tilemapRequestsSent++;this.tilemapStats.tilemapRequestsPending++;
var l=this;f=d.layer.tilemapCache.fetchTilemap(g,m,h,{timeout:6E3});f.then(function(f){l.tilemapStats.tilemapRequestsPending--;b=this._layerIndexByLayerViewId[c][d.uid];null!=b&&(a.layerInfo[c][b].tilemap=f);e.resolve()}.bind(this),function(){l.tilemapStats.tilemapRequestsPending--;l.tilemapStats.tilemapRequestErrors++;e.resolve()});return e},getTilemapTile:function(a){var b=a.lij[0];return b>k.TILEMAP_SIZE_EXP?p.getTileNLevelsUp(a,k.TILEMAP_SIZE_EXP):this._topLevelTilemapOnlyTiles[b]},_dispatchDataEvent:function(a,
b,c,d,f){d=this._layerIndexByLayerViewId[c][d.uid];if(null!=d)a[b](d,c,f);else t.warn("TerrainSurface: received data from unknown layer")},cancelRequest:function(a){var b=a.actualTileRequestPromise;void 0!==b?(null!==b&&this._streamDataSupplier.cancelRequest(b),a.cancel()):this._streamDataSupplier.cancelRequest(a)},_updateTileOverlayParams:function(){if(this.rootTiles){var a=this._iteratorPool.acquire();for(a.reset(this.rootTiles);!a.done;){var b=a.next();b.renderData&&this.overlayManager&&this.overlayManager.setOverlayParamsOfTile(b,
b.renderData,this._curOverlayOpacity)}this._iteratorPool.release(a);this._renderer.setNeedsRender()}},_updateOverlayOpacity:function(a){if(this.overlayManager&&(a=this.overlayManager.updateOpacity(a),!isNaN(a))){if(a!==this._curOverlayOpacity&&this.rootTiles){var b=this._iteratorPool.acquire();for(b.reset(this.rootTiles);!b.done;){var c=b.next();c.renderData&&c.renderData.overlayTexId&&(c.renderData.overlayOpacity=a)}this._iteratorPool.release(b)}this._curOverlayOpacity=a;this._renderer.setNeedsRender()}},
getStats:function(){var a=0,b=0,c=0,d=[],f=this._iteratorPool.acquire();for(f.reset(this.rootTiles);!f.done;){var e=f.next();a++;e.renderData&&(b++,e.visible&&(c++,e=e.lij[0],d[e]=null!=d[e]?d[e]+1:1))}this._iteratorPool.release(f);return{numNodes:a,numLeaves:b,numVisible:c,numVisiblePerLevel:d}},getTile:function(a){var b=a.split("/").map(JSON.parse);if(0===b[0])return this.rootTiles.forEach(function(a){if(a.lij[1]===b[1]&&a.lij[2]===b[2])return a}),null;var c=Math.pow(2,b[0]),d=Math.floor(b[1]/c),
f=Math.floor(b[2]/c),e;this.rootTiles.some(function(a){return a.lij[1]===d&&a.lij[2]===f?(e=a,!0):!1});if(e){for(c=1<<b[0]-1;e.lij[0]<b[0];){var g=b[1]&c?2:0;0<(b[2]&c)&&g++;if(!e.children[g])return console.log("Tile "+a+" doesn't exist, smallest ancestor is "+p.tile2str(e)),null;e=e.children[g];c>>=1}C(e.lij[0]===b[0]&&e.lij[1]===b[1]&&e.lij[2]===b[2],"not the right tile?");return e}return null},hasPendingUpdates:function(){if(this._streamDataSupplier._loader.hasPendingDownloads())return!0;var a=
this._iteratorPool.acquire();for(a.reset(this.rootTiles);!a.done;)if(0<a.next().pendingUpdates)return this._iteratorPool.release(a),!0;this._iteratorPool.release(a);return!1},setBorders:function(a){this._renderer.setBorders(a)},setDisableRendering:function(a){this._renderer.setDisableRendering(a)},_extentGetter:function(){return this._clippingExtent||this._rootExtent},_hasFixedExtent:function(){return!!this._clippingExtent},_wireframeSetter:function(a){this._renderer.setWireframe(a);this._set("wireframe",
a);this._view._stage.setRenderParams({earlyOcclusionPixelDraw:a})},_opacitySetter:function(a){this._renderer.setOpacity(a);this._set("opacity",a)},_skirtsSetter:function(a){this._renderer.setDrawSkirts(!!a);this._set("skirts",a)},_cullBackFacesSetter:function(a){this._renderer.setCullBackFaces(a);this._set("cullBackFaces",a)},_renderOrderSetter:function(a){this._renderer.setRenderOrder(a);this._set("renderOrder",a)},_frontMostTransparentSetter:function(a){this._renderer.setFrontMostTransparent(!!a);
this._set("frontMostTransparent",a)},_tileBackgroundSetter:function(a){a!==this.tileBackground&&this._renderer.updateTileBackground(a);this._set("tileBackground",a)},_velvetOvergroundSetter:function(a){a!==this.velvetOverground&&this._renderer.setVelvetOverground(a);this._set("velvetOverground",a)}});w.TOO_MANY_ROOT_TILES_AFTER_CHANGE="Cannot extend surface to encompass all layers because it would result in too many root tiles.";w.UPDATE_OVERLAY_MANAGER_BY_ELEVATION_UPDATES_DEFAULT=!1;return w});