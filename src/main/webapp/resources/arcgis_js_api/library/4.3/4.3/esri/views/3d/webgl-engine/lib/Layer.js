// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See https://js.arcgis.com/4.3/esri/copyright.txt for details.
//>>built
define("require exports dojo/has ./IdGen ./Octree ./PerformanceTimer ./Util ./gl-matrix ./ModelContentType".split(" "),function(g,p,q,k,l,r,h,m,n){var f=m.vec3d;g=function(){function b(a,d,c){this._parentStages=[];this._children=[];this.id=b._idGen.gen(c);this.name=a;d=d||{};this.group=d.group||"";this.state=d.state||"VISIBLE";this.interaction=d.interaction||"PICKABLE";this.translation=f.create(d.translation);this._extent=[f.createFrom(0,0,0),f.createFrom(1E3,1E3,1E3)];this._extentDirty=!0}b.prototype.getId=
function(){return this.id};b.prototype.getParentStages=function(){return this._parentStages};b.prototype.addParentStage=function(a){-1===this._parentStages.indexOf(a)&&this._parentStages.push(a)};b.prototype.removeParentStage=function(a){a=this._parentStages.indexOf(a);-1<a&&this._parentStages.splice(a,1)};b.prototype.getName=function(){return this.name};b.prototype.getGroup=function(){return this.group};b.prototype.getState=function(){return this.state};b.prototype.getInteraction=function(){return this.interaction};
b.prototype.getTranslation=function(){return this.translation};b.prototype.getObjectIds=function(){return this._children.map(function(a){return a.getId()})};b.prototype.getObjects=function(){return this._children};b.prototype.setState=function(a){this.state=a};b.prototype.getExtent=function(){this._updateExtent();return this._extent};b.prototype.addObject=function(a){this._children.push(a);a.addParentLayer(this);this.notifyDirty("layObjectAdded",a);this._invalidateExtent();this._spatialAccelerator&&
this._spatialAccelerator.add(a)};b.prototype.hasObject=function(a){return-1<this._children.indexOf(a)};b.prototype.removeObject=function(a){return null!=h.arrayRemove(this._children,a)?(a.removeParentLayer(this),this.notifyDirty("layObjectRemoved",a),this._invalidateExtent(),this._spatialAccelerator&&this._spatialAccelerator.remove(a),!0):!1};b.prototype.replaceObject=function(a,b){var d=this._children.indexOf(a);h.assert(-1<d,"Layer.replaceObject: layer doesn't contain specified object");this._children[d]=
b;a.removeParentLayer(this);b.addParentLayer(this);this.notifyDirty("layObjectReplaced",[a,b]);this._invalidateExtent();this._spatialAccelerator&&(this._spatialAccelerator.remove(a),this._spatialAccelerator.add(b))};b.prototype.notifyObjectBBChanged=function(a,b){this._spatialAccelerator&&this._spatialAccelerator.update(a,b)};b.prototype.getCenter=function(){this._updateExtent();var a=f.create();return f.lerp(this._extent[0],this._extent[1],.5,a)};b.prototype.getBSRadius=function(){this._updateExtent();
return.5*f.dist(this._extent[0],this._extent[1])};b.prototype.getSpatialQueryAccelerator=function(){if(this._spatialAccelerator)return this._spatialAccelerator;if(50<this._children.length)return this._createOctree(),this._spatialAccelerator};b.prototype.invalidateSpatialQueryAccelerator=function(){this._spatialAccelerator=null};b.prototype.notifyDirty=function(a,b,c,e){c=c||n.LAYER;e=e||this;for(var d=0;d<this._parentStages.length;d++)this._parentStages[d].notifyDirty(c,e,a,b)};b.prototype._createOctree=
function(){for(var a=this.getExtent(),b=0,c=0;3>c;c++)b=Math.max(b,a[1][c]-a[0][c]);c=f.create();f.lerp(a[0],a[1],.5,c);this._spatialAccelerator=new l(c,1.2*b);this._spatialAccelerator.add(this._children)};b.prototype._invalidateExtent=function(){this._extentDirty=!0};b.prototype._updateExtent=function(){if(this._extentDirty)if(0===this._children.length)this._extent=[[0,0,0],[0,0,0]];else{var a=this._children[0];this._extent=[f.create(a.getBBMin()),f.create(a.getBBMax())];for(a=0;a<this._children.length;++a)for(var b=
this._children[a],c=b.getBBMin(),b=b.getBBMax(),e=0;3>e;++e)this._extent[0][e]=Math.min(this._extent[0][e],c[e]),this._extent[1][e]=Math.max(this._extent[1][e],b[e]);this._extentDirty=!1}};return b}();g._idGen=new k;return g});