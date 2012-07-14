function ax(){}
function hx(){}
function Ax(){}
function Gi(a,b){ti(b,a)}
function _w(a,b){Iuc(b.b,a)}
function gx(a,b){Juc(b.b,a)}
function zx(a,b){Kuc(b.b,a)}
function w4b(a){this.b=a}
function D4b(a){this.b=a}
function avc(a){this.b=a}
function tvc(a){this.b=a}
function Quc(a){a.g=false;Onc(a.db)}
function Iuc(a,b){Ouc(a,(a.b,fw(b)),gw(b))}
function Juc(a,b){Puc(a,(a.b,fw(b)),gw(b))}
function Kuc(a,b){Quc(a,(a.b,fw(b),gw(b)))}
function Suc(){Ui();Tuc.call(this,new rvc)}
function $w(){$w=F0c;Zw=new tw(O5c,new ax)}
function fx(){fx=F0c;ex=new tw(j6c,new hx)}
function yx(){yx=F0c;xx=new tw(m6c,new Ax)}
function Ruc(a){!a.i&&(a.i=xoc(new avc(a)));jj(a)}
function Luc(a){if(a.i){_Qc(a.i.b);a.i=null}Zi(a,false)}
function rvc(){ovc.call(this);this.db[r2c]='Caption'}
function Ouc(a,b,c){if(!Inc){a.g=true;Pnc(a.db);a.e=b;a.f=c}}
function Buc(a,b){var c,d;d=bpc(a.c,b);c=bpc(d,1);return uq(c)}
function drc(a,b,c){var d;d=crc(a,b);!!d&&(d[P6c]=c.b,undefined)}
function Nuc(a,b){jKc(a.db,p2c,b);Lh(a.b,b+'-caption');jKc(Buc(a.k,1),b,q9c)}
function Muc(a,b){var c;c=b.target;if(rq(c)){return Oq(wq(Buc(a.k,0)),c)}return false}
function Puc(a,b,c){var d,e;if(a.g){d=b+gq(a.db);e=c+hq(a.db);if(d<a.c||d>=a.j||e<a.d){return}ej(a,d-a.e,e-a.f)}}
function fw(a){var b,c;b=a.c;if(b){return c=a.b,(c.clientX||0)-Gq(b)+Mq(b)+$q(b.ownerDocument)}return a.b.clientX||0}
function gw(a){var b,c;b=a.c;if(b){return c=a.b,(c.clientY||0)-Iq(b)+(b.scrollTop||0)+_q(b.ownerDocument)}return a.b.clientY||0}
function dj(a){a.x=true;if(!a.t){a.t=$doc.createElement(x2c);a.t.className='gwt-PopupPanelGlass';a.t.style[N3c]=(Dt(),O3c);a.t.style[Q3c]=0+(Au(),b3c);a.t.style[R3c]=S3c}}
function Tuc(a){var b,c;Ytc.call(this,false,true,F9c);ri(a);this.b=a;c=Buc(this.k,0);Jnc(c,this.b.db);Gi(this,this.b);CLc(uq(this.db))[r2c]='gwt-DialogBox';this.j=Xq($doc);this.c=Kq($doc);this.d=Lq($doc);b=new tvc(this);ki(this,b,($w(),$w(),Zw));ki(this,b,(yx(),yx(),xx));ki(this,b,(fx(),fx(),ex));ki(this,b,(sx(),sx(),rx));ki(this,b,(mx(),mx(),lx))}
function s4b(){var a,b,c,d,e,f,g,i,j,k,n;a=(g=new Suc,Nuc(g,'cwDialogBox'),hvc(g.b,'Sample DialogBox'),i=new HKc,i.f[R6c]=4,Li(g.k,i),$i(g),j=new qvc('This is an example of a standard dialog box component.'),EKc(i,j),drc(i,j,(Xyc(),Ryc)),k=new Smc((qMb(),fMb)),EKc(i,k),drc(i,k,Ryc),n=new arc(g9c,new D4b(g)),EKc(i,n),oF(),drc(i,n,Wyc),g);dj(a);a.w=true;e=new arc('Show Dialog Box',new w4b(a));d=new qvc('<br><br><br>This list box demonstrates that you can drag the popup over it. This obscure corner case renders incorrectly for many other libraries.');c=new DAc;c.db.size=1;for(b=10;b>0;--b){zAc(c,E9c+b,E9c+b,-1)}f=new HKc;f.f[R6c]=8;EKc(f,e);EKc(f,d);EKc(f,c);return f}
var E9c='item ';FHb(290,278,{},ax);_.Ac=function bx(a){_w(this,nkb(a,38))};_.Dc=function cx(){return Zw};var Zw;FHb(291,278,{},hx);_.Ac=function ix(a){gx(this,nkb(a,39))};_.Dc=function jx(){return ex};var ex;FHb(294,278,{},Ax);_.Ac=function Bx(a){zx(this,nkb(a,42))};_.Dc=function Cx(){return xx};var xx;FHb(822,1,x1c,w4b);_.Gc=function x4b(a){Wi(this.b);Ruc(this.b)};_.b=null;FHb(823,1,A1c);_.pc=function B4b(){iKb(this.b,s4b())};FHb(824,1,x1c,D4b);_.Gc=function E4b(a){Luc(this.b)};_.b=null;FHb(1087,1083,R0c,Suc);_.Pb=function Uuc(){try{oi(this.k)}finally{oi(this.b)}};_.Qb=function Vuc(){try{qi(this.k)}finally{qi(this.b)}};_.dc=function Wuc(){Luc(this)};_.Ub=function Xuc(a){switch(Roc(a.type)){case 4:case 8:case 64:case 16:case 32:if(!this.g&&!Muc(this,a)){return}}pi(this,a)};_.Ib=function Yuc(a){Nuc(this,a)};_.ec=function Zuc(a){var b;b=a.e;!a.b&&Roc(a.e.type)==4&&Muc(this,b)&&(b.preventDefault(),undefined);a.d&&(a.e,false)&&(a.b=true)};_.fc=function $uc(){Ruc(this)};_.b=null;_.c=0;_.d=0;_.e=0;_.f=0;_.g=false;_.i=null;_.j=0;FHb(1088,1,I1c,avc);_.Nc=function bvc(a){this.b.j=a.b};_.b=null;FHb(1089,1090,P0c,rvc);FHb(1093,1,{38:1,39:1,40:1,41:1,42:1,54:1},tvc);_.Jc=function uvc(a){};_.Kc=function vvc(a){};_.b=null;var Pwb=PRc(K7c,'CwDialogBox$1',822),Rwb=PRc(K7c,'CwDialogBox$3',824),zAb=PRc(G7c,'DialogBox',1087),xAb=PRc(G7c,'DialogBox$CaptionImpl',1089),yAb=PRc(G7c,'DialogBox$MouseHandler',1093),wAb=PRc(G7c,'DialogBox$1',1088),Oob=PRc(e8c,'MouseDownEvent',290),Tob=PRc(e8c,'MouseUpEvent',294),Qob=PRc(e8c,'MouseMoveEvent',291);n2c(wm)(15);