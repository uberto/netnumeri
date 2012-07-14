function hC(){hC=flc;gC=new Kic}
function zSb(a,b,c,d){var e;a.a.Zf(b,c);e=BSb(a.a.i,b,c);Uh(e,d,true)}
function iC(d,a){var b=d.a;for(var c in b){b.hasOwnProperty(c)&&a.pd(c)}}
function kC(d,a){a=String(a);var b=d.a;var c=b[a];(c==null||!b.hasOwnProperty(a))&&d.fd(a);return String(c)}
function mC(){hC();var a;a=nG(gC.kd(Nvc),60);if(!a){a=new lC;gC.md(Nvc,a)}return a}
function jC(c,b){try{typeof $wnd[b]!='object'&&oC(b);c.a=$wnd[b]}catch(a){oC(b)}}
function oC(a){throw new Ljc(zoc+a+"' is not a JavaScript object and cannot be used as a Dictionary")}
function lC(){this.b='Dictionary userInfo';jC(this,Nvc);if(!this.a){throw new Ljc("Cannot find JavaScript object with the name 'userInfo'")}}
function zib(){var a,b,c,d,e,f,g,i,j,k,n;f=new l3b;g=new RPb('<pre>var userInfo = {\n&nbsp;&nbsp;name: "Amelie Crutcher",\n&nbsp;&nbsp;timeZone: "EST",\n&nbsp;&nbsp;userID: "123",\n&nbsp;&nbsp;lastLogOn: "2/2/2006"\n};<\/pre>\n');g.cb.dir=Vnc;g.cb.style['textAlign']=moc;i3b(f,new RPb('<b>This example interacts with the following JavaScript variable:<\/b>'));i3b(f,g);j=new rSb;b=j.j;i=mC();e=(n=new Sic,iC(i,n),n);a=0;for(d=Ofc(nD(e.a));d.a.vd();){c=nG(Ufc(d),1);k=kC(i,c);iSb(j,0,a,c);zSb(b,0,a,'cw-DictionaryExample-header');iSb(j,1,a,k);zSb(b,1,a,'cw-DictionaryExample-data');++a}i3b(f,new RPb('<br><br>'));i3b(f,j);return f}
var Nvc='userInfo';Y_(343,1,{60:1},lC);_.fd=function nC(a){var b;b="Cannot find '"+a+"' in "+this;throw new Ljc(b)};_.tS=function pC(){return this.b};_.a=null;_.b=null;var gC;Y_(640,1,_lc);_.kc=function Fib(){G2(this.a,zib())};var xL=pac(Orc,jpc,343);Omc(km)(32);