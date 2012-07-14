function bC(){bC=Dkc;aC=new gic}
function hSb(a,b,c,d){var e;a.b.Zf(b,c);e=jSb(a.b.j,b,c);Uh(e,d,true)}
function cC(d,a){var b=d.b;for(var c in b){b.hasOwnProperty(c)&&a.pd(c)}}
function eC(d,a){a=String(a);var b=d.b;var c=b[a];(c==null||!b.hasOwnProperty(a))&&d.fd(a);return String(c)}
function gC(){bC();var a;a=hG(aC.kd(ovc),60);if(!a){a=new fC;aC.md(ovc,a)}return a}
function dC(c,b){try{typeof $wnd[b]!='object'&&iC(b);c.b=$wnd[b]}catch(a){iC(b)}}
function iC(a){throw new hjc($nc+a+"' is not a JavaScript object and cannot be used as a Dictionary")}
function fC(){this.c='Dictionary userInfo';dC(this,ovc);if(!this.b){throw new hjc("Cannot find JavaScript object with the name 'userInfo'")}}
function nib(){var a,b,c,d,e,f,g,i,j,k,n;f=new Q2b;g=new vPb('<pre>var userInfo = {\n&nbsp;&nbsp;name: "Amelie Crutcher",\n&nbsp;&nbsp;timeZone: "EST",\n&nbsp;&nbsp;userID: "123",\n&nbsp;&nbsp;lastLogOn: "2/2/2006"\n};<\/pre>\n');g.db.dir=unc;g.db.style['textAlign']=Nnc;N2b(f,new vPb('<b>This example interacts with the following JavaScript variable:<\/b>'));N2b(f,g);j=new _Rb;b=j.k;i=gC();e=(n=new oic,cC(i,n),n);a=0;for(d=kfc(hD(e.b));d.b.vd();){c=hG(qfc(d),1);k=eC(i,c);SRb(j,0,a,c);hSb(b,0,a,'cw-DictionaryExample-header');SRb(j,1,a,k);hSb(b,1,a,'cw-DictionaryExample-data');++a}N2b(f,new vPb('<br><br>'));N2b(f,j);return f}
var ovc='userInfo';R_(341,1,{60:1},fC);_.fd=function hC(a){var b;b="Cannot find '"+a+"' in "+this;throw new hjc(b)};_.tS=function jC(){return this.c};_.b=null;_.c=null;var aC;R_(637,1,xlc);_.lc=function tib(){u2(this.b,nib())};var tL=N9b(orc,Koc,341);kmc(km)(32);