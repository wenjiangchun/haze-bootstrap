// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See https://js.arcgis.com/4.3/esri/copyright.txt for details.
//>>built
define(["require","exports"],function(g,b){function d(a){return a.hasOwnProperty("listMode")?a.listMode:void 0}function e(a){return a.hasOwnProperty("minScale")?a.minScale:void 0}function f(a){return a.hasOwnProperty("maxScale")?a.maxScale:void 0}b.findLayerListMode=d;b.findLayerMinScale=e;b.findLayerMaxScale=f;b.findLayerVisibilityMode=function(a){return a.hasOwnProperty("visibilityMode")?a.visibilityMode:void 0};b.getNormalizedChildLayerProperty=function(a){var b="";"hide-children"!==a.listMode&&
["sublayers","layers"].some(function(c){if(a.hasOwnProperty(c))return b=c,!0});return b};b.canDisplayLayer=function(a){return"hide"!==d(a)};b.isLayerOutsideScaleRange=function(a,b){var c=e(a);a=f(a);return void 0!==c&&void 0!==a&&void 0!==b?0<c&&b>=c||0<a&&b<=a:!1}});