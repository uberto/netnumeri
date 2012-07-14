function Elb(){}
function YOb(a,b,c){$Ob(a,b,a.j.c);bPb(a,a.j.c-1,c)}
function Slb(){Slb=zlc;Clb=new l1((b2(),new V1(Bvc)),1,1)}
function Klb(){Klb=zlc;ulb=new l1((b2(),new V1(tvc)),15,16)}
function Mlb(){Mlb=zlc;wlb=new l1((b2(),new V1(vvc)),16,16)}
function Olb(){Olb=zlc;ylb=new l1((b2(),new V1(xvc)),16,16)}
function Plb(){Plb=zlc;zlb=new l1((b2(),new V1(yvc)),16,16)}
function Qlb(){Qlb=zlc;Alb=new l1((b2(),new V1(zvc)),16,16)}
function Rlb(){Rlb=zlc;Blb=new l1((b2(),new V1(Avc)),16,16)}
function Tlb(){Tlb=zlc;Dlb=new l1((b2(),new V1(Cvc)),16,16)}
function Ilb(){Ilb=zlc;slb=new l1((b2(),new V1(rvc)),32,32)}
function Jlb(){Jlb=zlc;tlb=new l1((b2(),new V1(svc)),32,32)}
function Llb(){Llb=zlc;vlb=new l1((b2(),new V1(uvc)),32,32)}
function Nlb(){Nlb=zlc;xlb=new l1((b2(),new V1(wvc)),32,32)}
function kPb(){kPb=zlc;jPb=oG(f_,Glc,1,['stackItemTop','stackItemMiddle'])}
function mPb(a){var b,c,d;b=gq(a);d=b.children[1];c=d.children[1];return gq(c)}
function plb(a,b,c,d,e){this.b=a;this.d=b;this.a=c;this.c=d;this.e=e}
function bPb(a,b,c){var d,e;if(b>=a.j.c){return}e=XJb(a.a,b*2).children[0];d=gq(e);aq(mPb(d),c)}
function dPb(a,b){if(b>=a.j.c||b<0||b==a.b){return}a.b>=0&&cPb(a,a.b,false);a.b=b;cPb(a,a.b,true)}
function ePb(a,b){var c,d,e,f;for(f=b,c=a.j.c;f<c;++f){e=XJb(a.a,f*2);d=gq(e);d[Dvc]=f;b==0?Th(d,Ivc,true):Th(d,Ivc,false)}}
function flb(a){var b,c,d,e,f;f=new D3b;f.e[brc]=4;for(c=w4(a.a),d=0,e=c.length;d<e;++d){b=c[d];A3b(f,new aMb(b))}return f}
function ZOb(a,b){var c,d;while(!!b&&b!=a.cb){c=Xp(b,Dvc);if(c!=null){d=Wp(b,Evc);return d==Om(a)?Wac(c):-1}b=iq(b)}return -1}
function x4(a){var b,c;b=yG(a.a.kd(dvc),150);if(b==null){c=oG(f_,Glc,1,[evc,fvc,gvc,hvc,ivc]);a.a.md(dvc,c);return c}else{return b}}
function w4(a){var b,c;b=yG(a.a.kd(Zuc),150);if(b==null){c=oG(f_,Glc,1,[$uc,_uc,avc,bvc,utc,cvc]);a.a.md(Zuc,c);return c}else{return b}}
function u4(a){var b,c;b=yG(a.a.kd(Huc),150);if(b==null){c=oG(f_,Glc,1,[Iuc,Juc,Kuc,Luc,Muc,Nuc,Ouc,Puc]);a.a.md(Huc,c);return c}else{return b}}
function v4(a){var b,c;b=yG(a.a.kd(Quc),150);if(b==null){c=oG(f_,Glc,1,[Ruc,Suc,Tuc,Uuc,Vuc,Wuc,Xuc,Yuc]);a.a.md(Quc,c);return c}else{return b}}
function dlb(a,b,c){var d;d=new E1;B1(d,p4b(new q4b(b.d,b.b,b.c,b.e,b.a)));C1((Ccc(d.a,Hnc),d),c);p2b(a,new G1(Jn(d.a.a)))}
function glb(a,b){var c,d;c=new zUb;c.e[brc]=0;yUb(c,(VTb(),TTb));wUb(c,new PHb(b));d=new hQb(a);d.cb[knc]=jvc;wUb(c,d);return c.cb.outerHTML}
function aPb(a,b,c){var d,e,f;d=NKb(a,b);if(d){e=2*c;f=XJb(a.a,e);Rp(a.a,f);f=XJb(a.a,e);Rp(a.a,f);a.b==c?(a.b=-1):a.b>c&&--a.b;ePb(a,c)}return d}
function lPb(){var a,b,c;b=nq($doc,Oqc);c=nq($doc,Pqc);Np(b,(IYb(),JYb(c)));b.style[lnc]=epc;b[brc]=0;b[crc]=0;for(a=0;a<jPb.length;++a){GIb(c,wPb(jPb[a]))}return b}
function _Ob(a,b){var c,d,e,f,g;f3b(a.cb,inc,b);f=a.a.children.length>>1;for(e=0;e<f;++e){g=gq(XJb(a.a,2*e));d=gq(g);c=gq(XJb(a.a,2*e+1));f3b(g,b,'text-wrapper'+e);f3b(c,b,Dtc+e);f3b(a.Vf(d),b,Hvc+e)}}
function nPb(){var a;kPb();OKb.call(this);a=nq($doc,Oqc);this.cb=a;this.a=nq($doc,Pqc);GIb(a,this.a);a[brc]=0;a[crc]=0;QJb();aKb(a,1);this.cb[knc]='gwt-StackPanel';Uh(this.cb,'gwt-DecoratedStackPanel')}
function cPb(a,b,c){var d,e,f,g,i;f=XJb(a.a,b*2);if(!f){return}d=gq(f);Th(d,'gwt-StackPanelItem-selected',c);i=XJb(a.a,b*2+1);Vh(i,c);I3b(a.j,b).Hb(c);g=XJb(a.a,(b+1)*2);if(g){e=gq(g);Th(e,'gwt-StackPanelItem-below-selected',c)}}
function elb(a){var b,c,d,e,f,g,i,j,k,n;j=new zUb;j.e[brc]=5;wUb(j,new PHb((Jlb(),tlb)));d=new fQb;wUb(j,d);i=new aj(true,false);i.Zb(j);k=new D3b;k.e[brc]=4;g=u4(a.a);c=v4(a.a);for(n=0;n<g.length;++n){f=g[n];b=c[n];e=new xLb(f);A3b(k,e);$h(e,new plb(d,f,b,e,i),($v(),$v(),Zv))}return k}
function hlb(a){var b,c,d,e,f,g,i,j;d=new Elb;f=new nPb;f.cb.style[lnc]=kvc;e=glb(lvc,(Nlb(),xlb));YOb(f,(g=new W1b(d),i=q2b(g.i,mvc),j=x4(a.a),dlb(i,(Mlb(),wlb),j[0]),dlb(i,(Klb(),ulb),j[1]),dlb(i,(Plb(),zlb),j[2]),dlb(i,(Olb(),ylb),j[3]),dlb(i,(Qlb(),Alb),j[4]),B2b(i,true,true),g),e);c=glb(nvc,(Llb(),vlb));YOb(f,flb(a),c);b=glb(ovc,(Ilb(),slb));YOb(f,elb(a),b);_Ob(f,'cwStackPanel');return f}
function $Ob(a,b,c){var d,e,f,g,i;i=nq($doc,Zqc);f=nq($doc,$qc);Np(i,(IYb(),JYb(f)));GIb(f,lPb());g=nq($doc,Zqc);e=nq($doc,$qc);Np(g,JYb(e));c=GKb(a,b,c);d=c*2;IIb(a.a,g,d);IIb(a.a,i,d);Th(f,'gwt-StackPanelItem',true);f[Evc]=Om(a);f[jnc]=Fvc;Th(e,'gwt-StackPanelContent',true);e[jnc]=epc;e[Gvc]=Koc;LKb(a,b,e,c,false);ePb(a,c);if(a.b==-1){dPb(a,0)}else{cPb(a,c,false);a.b>=c&&++a.b;cPb(a,a.b,true)}}
var Dvc='__index',Evc='__owner',Huc='cwStackPanelContacts',Quc='cwStackPanelContactsEmails',Zuc='cwStackPanelFilters',dvc='cwStackPanelMailFolders',Ivc='gwt-StackPanelItem-first';h0(678,1,tmc);_.kc=function nlb(){R2(this.b,hlb(this.a))};h0(679,1,qmc,plb);_.Cc=function qlb(a){var b,c;dQb(this.b,this.d+pvc+this.a+qvc);b=Dq(this.c.cb)+14;c=Eq(this.c.cb)+14;Ti(this.e,b,c);this.e.ac()};_.a=null;_.b=null;_.c=null;_.d=null;_.e=null;h0(680,1,{},Elb);_.Zd=function Flb(){return Rlb(),Blb};_.Yd=function Glb(){return Slb(),Clb};_.$d=function Hlb(){return Tlb(),Dlb};var slb=null,tlb=null,ulb=null,vlb=null,wlb=null,xlb=null,ylb=null,zlb=null,Alb=null,Blb=null,Clb=null,Dlb=null;h0(999,967,Jlc);_.Vf=function fPb(a){return a};_.Pb=function gPb(a){var b,c;if(PJb(a.type)==1){c=a.srcElement;b=ZOb(this,c);b!=-1&&dPb(this,b)}di(this,a)};_.Db=function hPb(a){_Ob(this,a)};_.Vb=function iPb(a){return aPb(this,a,J3b(this.j,a))};_.a=null;_.b=-1;h0(998,999,Jlc,nPb);_.Vf=function oPb(a){return mPb(a)};var jPb;var zQ=Jac(Vrc,'CwStackPanel$2',679),hX=Jac(Trc,'StackPanel',999),YU=Jac(Trc,'DecoratedStackPanel',998),AQ=Jac(Vrc,'CwStackPanel_Images_default_InlineClientBundleGenerator',680);gnc(jm)(23);