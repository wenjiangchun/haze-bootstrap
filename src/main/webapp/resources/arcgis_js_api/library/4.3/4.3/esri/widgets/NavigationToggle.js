// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See https://js.arcgis.com/4.3/esri/copyright.txt for details.
//>>built
define("require exports ../core/tsSupport/declareExtendsHelper ../core/tsSupport/decorateHelper ../core/accessorSupport/decorators ./support/widget ./Widget ./NavigationToggle/NavigationToggleViewModel dojo/i18n!./NavigationToggle/nls/NavigationToggle".split(" "),function(b,n,k,a,d,c,l,h,m){b=function(b){function a(a){a=b.call(this)||this;a.view=null;a.viewModel=new h;return a}k(a,b);Object.defineProperty(a.prototype,"layout",{set:function(a){"horizontal"!==a&&(a="vertical");this._set("layout",a)},
enumerable:!0,configurable:!0});a.prototype.toggle=function(){};a.prototype.render=function(){var a="disabled"===this.get("viewModel.state"),b="pan"===this.get("viewModel.navigationMode"),d=(e={},e["esri-disabled"]=a,e),e=(f={},f["esri-navigation-toggle__button--active"]=b,f),b=(g={},g["esri-navigation-toggle__button--active"]=!b,g);return c.jsxFactory.createElement("div",{bind:this,class:"esri-navigation-toggle esri-widget",classes:d,onclick:this._toggle,onkeydown:this._toggle,tabIndex:a?-1:0,title:m.toggle},
c.jsxFactory.createElement("div",{class:c.join("esri-navigation-toggle__button esri-widget-button","esri-navigation-toggle__button--pan"),classes:e},c.jsxFactory.createElement("span",{class:"esri-icon-pan"})),c.jsxFactory.createElement("div",{class:c.join("esri-navigation-toggle__button esri-widget-button","esri-navigation-toggle__button--rotate"),classes:b},c.jsxFactory.createElement("span",{class:"esri-icon-rotate"})));var e,f,g};a.prototype._toggle=function(){this.toggle()};return a}(d.declared(l));
a([d.property({value:"vertical"}),c.renderable()],b.prototype,"layout",null);a([d.aliasOf("viewModel.view"),c.renderable()],b.prototype,"view",void 0);a([d.property({type:h}),c.renderable(["viewModel.state","viewModel.navigationMode"])],b.prototype,"viewModel",void 0);a([d.aliasOf("viewModel.toggle")],b.prototype,"toggle",null);a([c.accessibleHandler()],b.prototype,"_toggle",null);return b=a([d.subclass("esri.widgets.NavigationToggle")],b)});