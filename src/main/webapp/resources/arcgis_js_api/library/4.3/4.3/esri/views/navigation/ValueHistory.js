// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See https://js.arcgis.com/4.3/esri/copyright.txt for details.
//>>built
define(["require","exports","../../core/now"],function(c,d,e){c=function(){function a(b){this._maximumAge=b;this._values=[]}Object.defineProperty(a.prototype,"values",{get:function(){return this._values},enumerable:!0,configurable:!0});a.prototype.reset=function(){this._values=[]};a.prototype.add=function(b,a){a=void 0!==a?a:e();this._values.push({value:b,timeStamp:a});this._cleanup(a)};Object.defineProperty(a.prototype,"newest",{get:function(){var a=this._values.length;if(0<a)return this._values[a-
1]},enumerable:!0,configurable:!0});Object.defineProperty(a.prototype,"oldest",{get:function(){if(0<this._values.length)return this._values[0]},enumerable:!0,configurable:!0});a.prototype._cleanup=function(a){for(;0<this._values.length;)if(this._values[0].timeStamp+this._maximumAge<a)this._values.shift();else break};return a}();d.ValueHistory=c});