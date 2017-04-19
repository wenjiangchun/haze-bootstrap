//>>built
define("dojo/_base/kernel dojo/_base/lang dojox/string/tokenize dojo/_base/json dojo/dom dojo/_base/xhr dojox/string/Builder dojo/_base/Deferred".split(" "),function(q,f,m,D,r,n,t,p){q.experimental("dojox.dtl");var d=f.getObject("dojox.dtl",!0);d._base={};d.TOKEN_BLOCK=-1;d.TOKEN_VAR=-2;d.TOKEN_COMMENT=-3;d.TOKEN_TEXT=3;d._Context=f.extend(function(a){a&&(f._mixin(this,a),a.get&&(this._getter=a.get,delete this.get))},{push:function(){var a=this,b=f.delegate(this);b.pop=function(){return a};return b},
pop:function(){throw Error("pop() called on empty Context");},get:function(a,b){var c=this._normalize;if(this._getter){var e=this._getter(a);if(void 0!==e)return c(e)}return void 0!==this[a]?c(this[a]):b},_normalize:function(a){a instanceof Date&&(a.year=a.getFullYear(),a.month=a.getMonth()+1,a.day=a.getDate(),a.date=a.year+"-"+("0"+a.month).slice(-2)+"-"+("0"+a.day).slice(-2),a.hour=a.getHours(),a.minute=a.getMinutes(),a.second=a.getSeconds(),a.microsecond=a.getMilliseconds());return a},update:function(a){var b=
this.push();a&&f._mixin(this,a);return b}});var u=/("(?:[^"\\]*(?:\\.[^"\\]*)*)"|'(?:[^'\\]*(?:\\.[^'\\]*)*)'|[^\s]+)/g,v=/\s+/g,w=function(a,b){a=a||v;a instanceof RegExp||(a=new RegExp(a,"g"));if(!a.global)throw Error("You must use a globally flagged RegExp with split "+a);a.exec("");for(var c,e=[],d=0,f=0;(c=a.exec(this))&&!(e.push(this.slice(d,a.lastIndex-c[0].length)),d=a.lastIndex,b&&++f>b-1););e.push(this.slice(d));return e};d.Token=function(a,b){this.token_type=a;this.contents=new String(f.trim(b));
this.contents.split=w;this.split=function(){return String.prototype.split.apply(this.contents,arguments)}};d.Token.prototype.split_contents=function(a){var b,c=[],e=0;for(a=a||999;e++<a&&(b=u.exec(this.contents));)b=b[0],'"'==b.charAt(0)&&'"'==b.slice(-1)?c.push('"'+b.slice(1,-1).replace('\\"','"').replace("\\\\","\\")+'"'):"'"==b.charAt(0)&&"'"==b.slice(-1)?c.push("'"+b.slice(1,-1).replace("\\'","'").replace("\\\\","\\")+"'"):c.push(b);return c};var g=d.text={_get:function(a,b,c){a=d.register.get(a,
b.toLowerCase(),c);if(!a){if(!c)throw Error("No tag found for "+b);return null}c=a[1];a=a[2];-1!=c.indexOf(":")&&(c=c.split(":"),c=c.pop());var e=a;/\./.test(a)&&(a=a.replace(/\./g,"/"));require([a],function(){});a=f.getObject(e);return a[c||b]||a[b+"_"]||a[c+"_"]},getTag:function(a,b){return g._get("tag",a,b)},getFilter:function(a,b){return g._get("filter",a,b)},getTemplate:function(a){return new d.Template(g.getTemplateString(a))},getTemplateString:function(a){return n._getText(a.toString())||""},
_resolveLazy:function(a,b,c){return b?c?c.fromJson(n._getText(a))||{}:d.text.getTemplateString(a):n.get({handleAs:c?"json":"text",url:a})},_resolveTemplateArg:function(a,b){return g._isTemplate(a)?b?a:(b=new p,b.callback(a),b):g._resolveLazy(a,b)},_isTemplate:function(a){return void 0===a||"string"==typeof a&&(a.match(/^\s*[<{]/)||-1!=a.indexOf(" "))},_resolveContextArg:function(a,b){return a.constructor==Object?b?a:(b=new p,b.callback(a),b):g._resolveLazy(a,b,!0)},_re:/(?:\{\{\s*(.+?)\s*\}\}|\{%\s*(load\s*)?(.+?)\s*%\})/g,
tokenize:function(a){return m(a,g._re,g._parseDelims)},_parseDelims:function(a,b,c){if(a)return[d.TOKEN_VAR,a];if(b)for(a=f.trim(c).split(/\s+/g),b=0;c=a[b];b++)/\./.test(c)&&(c=c.replace(/\./g,"/")),require([c]);else return[d.TOKEN_BLOCK,c]}};d.Template=f.extend(function(a,b){a=b?a:g._resolveTemplateArg(a,!0)||"";a=g.tokenize(a);this.nodelist=(new d._Parser(a)).parse()},{update:function(a,b){return g._resolveContextArg(b).addCallback(this,function(b){var c=this.render(new d._Context(b));a.forEach?
a.forEach(function(a){a.innerHTML=c}):r.byId(a).innerHTML=c;return this})},render:function(a,b){b=b||this.getBuffer();a=a||new d._Context({});return this.nodelist.render(a,b)+""},getBuffer:function(){return new t}});var x=/\{\{\s*(.+?)\s*\}\}/g;d.quickFilter=function(a){if(!a)return new d._NodeList;if(-1==a.indexOf("{%"))return new d._QuickNodeList(m(a,x,function(a){return new d._Filter(a)}))};d._QuickNodeList=f.extend(function(a){this.contents=a},{render:function(a,b){for(var c=0,d=this.contents.length;c<
d;c++)b=this.contents[c].resolve?b.concat(this.contents[c].resolve(a)):b.concat(this.contents[c]);return b},dummyRender:function(a){return this.render(a,d.Template.prototype.getBuffer()).toString()},clone:function(a){return this}});d._Filter=f.extend(function(a){if(!a)throw Error("Filter must be called with variable name");this.contents=a;var b=this._cache[a];b?(this.key=b[0],this.filters=b[1]):(this.filters=[],m(a,this._re,this._tokenize,this),this._cache[a]=[this.key,this.filters])},{_cache:{},
_re:/(?:^_\("([^\\"]*(?:\\.[^\\"])*)"\)|^"([^\\"]*(?:\\.[^\\"]*)*)"|^([a-zA-Z0-9_.]+)|\|(\w+)(?::(?:_\("([^\\"]*(?:\\.[^\\"])*)"\)|"([^\\"]*(?:\\.[^\\"]*)*)"|([a-zA-Z0-9_.]+)|'([^\\']*(?:\\.[^\\']*)*)'))?|^'([^\\']*(?:\\.[^\\']*)*)')/g,_values:{0:'"',1:'"',2:"",8:'"'},_args:{4:'"',5:'"',6:"",7:"'"},_tokenize:function(){for(var a,b,c=0,d=[];c<arguments.length;c++)d[c]=void 0!==arguments[c]&&"string"==typeof arguments[c]&&arguments[c];if(this.key){for(a in this._args)if(d[a]){b=arguments[a];"'"==this._args[a]?
b=b.replace(/\\'/g,"'"):'"'==this._args[a]&&(b=b.replace(/\\"/g,'"'));b=[!this._args[a],b];break}a=g.getFilter(arguments[3]);if(!f.isFunction(a))throw Error(arguments[3]+" is not registered as a filter");this.filters.push([a,b])}else for(a in this._values)if(d[a]){this.key=this._values[a]+arguments[a]+this._values[a];break}},getExpression:function(){return this.contents},resolve:function(a){if(void 0===this.key)return"";for(var b=this.resolvePath(this.key,a),c=0,d;d=this.filters[c];c++)b=d[1]?d[1][0]?
d[0](b,this.resolvePath(d[1][1],a)):d[0](b,d[1][1]):d[0](b);return b},resolvePath:function(a,b){var c;c=a.charAt(0);var e=a.slice(-1);if(isNaN(parseInt(c)))if('"'==c&&c==e)a=a.slice(1,-1);else{if("true"==a)return!0;if("false"==a)return!1;if("null"==a||"None"==a)return null;c=a.split(".");a=b.get(c[0]);f.isFunction(a)&&(b=b.getThis&&b.getThis(),a=a.alters_data?"":b?a.call(b):"");for(b=1;b<c.length;b++)if(e=c[b],a){var g=a;if(f.isObject(a)&&"items"==e&&void 0===a[e]){var e=[],h;for(h in a)e.push([h,
a[h]]);a=e}else{if(a.get&&f.isFunction(a.get)&&a.get.safe)a=a.get(e);else if(void 0===a[e]){a=a[e];break}else a=a[e];f.isFunction(a)?a=a.alters_data?"":a.call(g):a instanceof Date&&(a=d._Context.prototype._normalize(a))}}else return""}else a=-1==a.indexOf(".")?parseInt(a):parseFloat(a);return a}});d._TextNode=d._Node=f.extend(function(a){this.contents=a},{set:function(a){this.contents=a;return this},render:function(a,b){return b.concat(this.contents)},isEmpty:function(){return!f.trim(this.contents)},
clone:function(){return this}});d._NodeList=f.extend(function(a){this.contents=a||[];this.last=""},{push:function(a){this.contents.push(a);return this},concat:function(a){this.contents=this.contents.concat(a);return this},render:function(a,b){for(var c=0;c<this.contents.length;c++)if(b=this.contents[c].render(a,b),!b)throw Error("Template must return buffer");return b},dummyRender:function(a){return this.render(a,d.Template.prototype.getBuffer()).toString()},unrender:function(a,b){return b},clone:function(){return this},
rtrim:function(){for(;;)if(i=this.contents.length-1,this.contents[i]instanceof d._TextNode&&this.contents[i].isEmpty())this.contents.pop();else break;return this}});d._VarNode=f.extend(function(a){this.contents=new d._Filter(a)},{render:function(a,b){a=this.contents.resolve(a)||"";a.safe||(a=d._base.escape(""+a));return b.concat(a)}});d._noOpNode=new function(){this.render=this.unrender=function(a,b){return b};this.clone=function(){return this}};d._Parser=f.extend(function(a){this.contents=a},{i:0,
parse:function(a){var b={},c;a=a||[];for(var e=0;e<a.length;e++)b[a[e]]=!0;for(e=new d._NodeList;this.i<this.contents.length;)if(c=this.contents[this.i++],"string"==typeof c)e.push(new d._TextNode(c));else{var f=c[0];c=c[1];if(f==d.TOKEN_VAR)e.push(new d._VarNode(c));else if(f==d.TOKEN_BLOCK){if(b[c])return--this.i,e;var h=c.split(/\s+/g);h.length&&(h=h[0],(h=g.getTag(h))&&e.push(h(this,new d.Token(f,c))))}}if(a.length)throw Error("Could not find closing tag(s): "+a.toString());this.contents.length=
0;return e},next_token:function(){var a=this.contents[this.i++];return new d.Token(a[0],a[1])},delete_first_token:function(){this.i++},skip_past:function(a){for(;this.i<this.contents.length;){var b=this.contents[this.i++];if(b[0]==d.TOKEN_BLOCK&&b[1]==a)return}throw Error("Unclosed tag found when looking for "+a);},create_variable_node:function(a){return new d._VarNode(a)},create_text_node:function(a){return new d._TextNode(a||"")},getTemplate:function(a){return new d.Template(a)}});d.register={_registry:{attributes:[],
tags:[],filters:[]},get:function(a,b){a=d.register._registry[a+"s"];for(var c=0,e;e=a[c];c++)if("string"==typeof e[0]){if(e[0]==b)return e}else if(b.match(e[0]))return e},getAttributeTags:function(){for(var a=[],b=d.register._registry.attributes,c=0,e;e=b[c];c++)if(3==e.length)a.push(e);else{var g=f.getObject(e[1]);g&&f.isFunction(g)&&(e.push(g),a.push(e))}return a},_any:function(a,b,c){for(var e in c)for(var g=0,h;h=c[e][g];g++){var k=h;f.isArray(h)&&(k=h[0],h=h[1]);if("string"==typeof k){if("attr:"==
k.substr(0,5)){var l=h;"attr:"==l.substr(0,5)&&(l=l.slice(5));d.register._registry.attributes.push([l.toLowerCase(),b+"."+e+"."+l])}k=k.toLowerCase()}d.register._registry[a].push([k,h,b+"."+e])}},tags:function(a,b){d.register._any("tags",a,b)},filters:function(a,b){d.register._any("filters",a,b)}};var y=/&/g,z=/</g,A=/>/g,B=/'/g,C=/"/g;d._base.escape=function(a){return d.mark_safe(a.replace(y,"\x26amp;").replace(z,"\x26lt;").replace(A,"\x26gt;").replace(C,"\x26quot;").replace(B,"\x26#39;"))};d._base.safe=
function(a){"string"==typeof a&&(a=new String(a));"object"==typeof a&&(a.safe=!0);return a};d.mark_safe=d._base.safe;d.register.tags("dojox.dtl.tag",{date:["now"],logic:["if","for","ifequal","ifnotequal"],loader:["extends","block","include","load","ssi"],misc:"comment debug filter firstof spaceless templatetag widthratio with".split(" "),loop:["cycle","ifchanged","regroup"]});d.register.filters("dojox.dtl.filter",{dates:["date","time","timesince","timeuntil"],htmlstrings:["linebreaks","linebreaksbr",
"removetags","striptags"],integers:["add","get_digit"],lists:"dictsort dictsortreversed first join length length_is random slice unordered_list".split(" "),logic:["default","default_if_none","divisibleby","yesno"],misc:["filesizeformat","pluralize","phone2numeric","pprint"],strings:"addslashes capfirst center cut fix_ampersands floatformat iriencode linenumbers ljust lower make_list rjust slugify stringformat title truncatewords truncatewords_html upper urlencode urlize urlizetrunc wordcount wordwrap".split(" ")});
d.register.filters("dojox.dtl",{_base:["escape","safe"]});return d});