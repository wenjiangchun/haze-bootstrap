// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See https://js.arcgis.com/4.3/esri/copyright.txt for details.
//>>built
define(["../../../core/Accessor","../support/earthUtils"],function(b,c){c=4*c.earthRadius;b=b.createSubclass([],{declaredClass:"esri.views.3d.constraints.SceneViewAltitudeConstraint",properties:{mode:{value:"auto"},min:{value:-Infinity,set:function(a){this._set("min",a);this._get("max")<a&&this._set("max",a);this.mode="manual"}},max:{value:c,set:function(a){this._set("max",a);this._get("min")>a&&this._set("min",a);this.mode="manual"}}},autoUpdate:function(a,b){"auto"===this.mode&&(this._get("min")!==
a&&this._set("min",a),this._get("max")!==b&&this._set("max",b))},scale:function(a){this._set("max",this._get("max")*a);this._set("min",this._get("min")*a)}});b.MIN_DEFAULT=-Infinity;b.MAX_DEFAULT=c;return b});