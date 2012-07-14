function CC(){CC=tlc;BC=new Yic}
function OSb(a,b,c,d){var e;a.b.bg(b,c);e=QSb(a.b.j,b,c);di(e,d,true)}
function DC(d,a){var b=d.b;for(var c in b){b.hasOwnProperty(c)&&a.td(c)}}
function FC(d,a){a=String(a);var b=d.b;var c=b[a];(c==null||!b.hasOwnProperty(a))&&d.kd(a);return String(c)}
function HC(){CC();var a;a=IG(BC.od(lwc),60);if(!a){a=new GC;BC.qd(lwc,a)}return a}
function EC(c,b){try{typeof $wnd[b]!='object'&&JC(b);c.b=$wnd[b]}catch(a){JC(b)}}
function JC(a){throw new Zjc(Qoc+a+"' is not a JavaScript object and cannot be used as a Dictionary")}
function GC(){this.c='Dictionary userInfo';EC(this,lwc);if(!this.b){throw new Zjc("Cannot find JavaScript object with the name 'userInfo'")}}
function Rib(){var a,b,c,d,e,f,g,i,j,k,n;f=new v3b;g=new eQb('<pre>var userInfo = {\n&nbsp;&nbsp;name: "Amelie Crutcher",\n&nbsp;&nbsp;timeZone: "EST",\n&nbsp;&nbsp;userID: "123",\n&nbsp;&nbsp;lastLogOn: "2/2/2006"\n};<\/pre>\n');g.db.dir=koc;g.db.style['textAlign']=Doc;s3b(f,new eQb('<b>This example interacts with the following JavaScript variable:<\/b>'));s3b(f,g);j=new GSb;b=j.k;i=HC();e=(n=new ejc,DC(i,n),n);a=0;for(d=agc(ID(e.b));d.b.zd();){c=IG(ggc(d),1);k=FC(i,c);xSb(j,0,a,c);OSb(b,0,a,'cw-DictionaryExample-header');xSb(j,1,a,k);OSb(b,1,a,'cw-DictionaryExample-data');++a}s3b(f,new eQb('<br><br>'));s3b(f,j);return f}
var lwc='userInfo';t0(342,1,{60:1},GC);_.kd=function IC(a){var b;b="Cannot find '"+a+"' in "+this;throw new Zjc(b)};_.tS=function KC(){return this.c};_.b=null;_.c=null;var BC;t0(638,1,omc);_.pc=function Xib(){Y2(this.b,Rib())};var WL=Dac(lsc,Apc,342);bnc(wm)(32);