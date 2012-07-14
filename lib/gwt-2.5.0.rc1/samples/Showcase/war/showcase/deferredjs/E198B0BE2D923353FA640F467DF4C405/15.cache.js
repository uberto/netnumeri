function Bw(){}
function Iw(){}
function _w(){}
function vi(a,b){ii(b,a)}
function Aw(a,b){NOb(b.b,a)}
function Hw(a,b){OOb(b.b,a)}
function $w(a,b){POb(b.b,a)}
function Iob(a){this.b=a}
function Pob(a){this.b=a}
function fPb(a){this.b=a}
function yPb(a){this.b=a}
function VOb(a){a.g=false;XHb(a.db)}
function XOb(){YOb.call(this,new wPb)}
function NOb(a,b){TOb(a,(a.b,Gv(b)),Hv(b))}
function OOb(a,b){UOb(a,(a.b,Gv(b)),Hv(b))}
function POb(a,b){VOb(a,(a.b,Gv(b),Hv(b)))}
function WOb(a){!a.i&&(a.i=HIb(new fPb(a)));Zi(a)}
function zw(){zw=Dkc;yw=new Uv(mpc,new Bw)}
function Gw(){Gw=Dkc;Fw=new Uv(Hpc,new Iw)}
function Zw(){Zw=Dkc;Yw=new Uv(Kpc,new _w)}
function wPb(){tPb.call(this);this.db[omc]='Caption'}
function QOb(a){if(a.i){Z8b(a.i.b);a.i=null}Ni(a,false)}
function TOb(a,b,c){if(!RHb){a.g=true;YHb(a.db);a.e=b;a.f=c}}
function GOb(a,b){var c,d;d=lJb(a.c,b);c=lJb(d,1);return gq(c)}
function iLb(a,b,c){var d;d=hLb(a,b);!!d&&(d[hqc]=c.b,undefined)}
function SOb(a,b){s2b(a.db,mmc,b);Ah(a.b,b+'-caption');s2b(GOb(a.k,1),b,Jsc)}
function ROb(a,b){var c;c=b.target;if(dq(c)){return rq(iq(GOb(a.k,0)),c)}return false}
function UOb(a,b,c){var d,e;if(a.g){d=b+tq(a.db);e=c+uq(a.db);if(d<a.c||d>=a.j||e<a.d){return}Ui(a,d-a.e,e-a.f)}}
function Hv(a){var b,c;b=a.c;if(b){return c=a.b,(c.clientY||0)-uq(b)+(b.scrollTop||0)+Bq(b.ownerDocument)}return a.b.clientY||0}
function Gv(a){var b,c;b=a.c;if(b){return c=a.b,(c.clientX||0)-tq(b)+(b.scrollLeft||0)+Aq(b.ownerDocument)}return a.b.clientX||0}
function Ti(a){a.x=true;if(!a.t){a.t=$doc.createElement(umc);a.t.className='gwt-PopupPanelGlass';a.t.style[Knc]=(ct(),Lnc);a.t.style[Nnc]=0+(_t(),$mc);a.t.style[Onc]=Pnc}}
function YOb(a){var b,c;bOb.call(this,false,true,Ysc);gi(a);this.b=a;c=GOb(this.k,0);SHb(c,this.b.db);vi(this,this.b);iq(gq(this.db))[omc]='gwt-DialogBox';this.j=xq($doc);this.c=0;this.d=0;b=new yPb(this);_h(this,b,(zw(),zw(),yw));_h(this,b,(Zw(),Zw(),Yw));_h(this,b,(Gw(),Gw(),Fw));_h(this,b,(Tw(),Tw(),Sw));_h(this,b,(Nw(),Nw(),Mw))}
function Eob(){var a,b,c,d,e,f,g,i,j,k,n;a=(g=new XOb,SOb(g,'cwDialogBox'),mPb(g.b,'Sample DialogBox'),i=new Q2b,i.f[jqc]=4,Ai(g.k,i),Oi(g),j=new vPb('This is an example of a standard dialog box component.'),N2b(i,j),iLb(i,j,(eTb(),$Sb)),k=new _Gb((C4(),r4)),N2b(i,k),iLb(i,k,$Sb),n=new fLb(zsc,new Pob(g)),N2b(i,n),uC(),iLb(i,n,dTb),g);Ti(a);a.w=true;e=new fLb('Show Dialog Box',new Iob(a));d=new vPb('<br><br><br>This list box demonstrates that you can drag the popup over it. This obscure corner case renders incorrectly for many other libraries.');c=new MUb;c.db.size=1;for(b=10;b>0;--b){IUb(c,Xsc+b,Xsc+b,-1)}f=new Q2b;f.f[jqc]=8;N2b(f,e);N2b(f,d);N2b(f,c);return f}
var Xsc='item ';R_(289,277,{},Bw);_.wc=function Cw(a){Aw(this,hG(a,38))};_.zc=function Dw(){return yw};var yw;R_(290,277,{},Iw);_.wc=function Jw(a){Hw(this,hG(a,39))};_.zc=function Kw(){return Fw};var Fw;R_(293,277,{},_w);_.wc=function ax(a){$w(this,hG(a,42))};_.zc=function bx(){return Yw};var Yw;R_(734,1,ulc,Iob);_.Cc=function Job(a){Ki(this.b);WOb(this.b)};_.b=null;R_(735,1,xlc);_.lc=function Nob(){u2(this.b,Eob())};R_(736,1,ulc,Pob);_.Cc=function Qob(a){QOb(this.b)};_.b=null;R_(996,992,Okc,XOb);_.Lb=function ZOb(){try{di(this.k)}finally{di(this.b)}};_.Mb=function $Ob(){try{fi(this.k)}finally{fi(this.b)}};_._b=function _Ob(){QOb(this)};_.Qb=function aPb(a){switch(_Ib(a.type)){case 4:case 8:case 64:case 16:case 32:if(!this.g&&!ROb(this,a)){return}}ei(this,a)};_.Eb=function bPb(a){SOb(this,a)};_.ac=function cPb(a){var b;b=a.e;!a.b&&_Ib(a.e.type)==4&&ROb(this,b)&&(b.preventDefault(),undefined);a.d&&(a.e,false)&&(a.b=true)};_.bc=function dPb(){WOb(this)};_.b=null;_.c=0;_.d=0;_.e=0;_.f=0;_.g=false;_.i=null;_.j=0;R_(997,1,Flc,fPb);_.Jc=function gPb(a){this.b.j=a.b};_.b=null;R_(998,999,Mkc,wPb);R_(1002,1,{38:1,39:1,40:1,41:1,42:1,54:1},yPb);_.Fc=function zPb(a){};_.Gc=function APb(a){};_.b=null;var aR=N9b(brc,'CwDialogBox$1',734),cR=N9b(brc,'CwDialogBox$3',736),KU=N9b(Zqc,'DialogBox',996),IU=N9b(Zqc,'DialogBox$CaptionImpl',998),JU=N9b(Zqc,'DialogBox$MouseHandler',1002),HU=N9b(Zqc,'DialogBox$1',997),GK=N9b(xrc,'MouseDownEvent',289),LK=N9b(xrc,'MouseUpEvent',293),IK=N9b(xrc,'MouseMoveEvent',290);kmc(km)(15);