define("frontend-js-metal-web@1.0.8/metal/src/string/string-min", ["exports"], function(n){"use strict";function t(n,t){if(!(n instanceof t))throw new TypeError("Cannot call a class as a function")}Object.defineProperty(n,"__esModule",{value:!0});var r=function(){function n(){t(this,n)}return (n.collapseBreakingSpaces=function(n){return n.replace(/[\t\r\n ]+/g," ").replace(/^[\t\r\n ]+|[\t\r\n ]+$/g,"")}, n.escapeRegex=function(n){return String(n).replace(/([-()\[\]{}+?*.$\^|,:#<!\\])/g,"\\$1").replace(/\x08/g,"\\x08")}, n.getRandomString=function(){var n=2147483648;return Math.floor(Math.random()*n).toString(36)+Math.abs(Math.floor(Math.random()*n)^Date.now()).toString(36)}, n.hashCode=function(n){for(var t=0,r=0,e=n.length;e>r;r++)t=31*t+n.charCodeAt(r),t%=4294967296;return t}, n.replaceInterval=function(n,t,r,e){return n.substring(0,t)+e+n.substring(r)}, n)}();n["default"]=r});