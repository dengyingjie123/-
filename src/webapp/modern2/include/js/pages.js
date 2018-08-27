/**

pagePool.put('customer-save', {
    id:'customer-save',
    name:'customer_save',
    url:'customer_save.jsp',
    preprocess:function(content, url, next){
        alert('init:' + this.id);
        next(content);
    },
    onPageInit:function (app, page) {

    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});

 login-mobile-code
 mine-change-description
 circle-save


 */

var pagePool = new Map;

pagePool.put('circle-list', {
    id:'circle-list',
    name:'circle_list',
    url:'circle_list.jsp',
    preprocess:function(content, url, next){
        var compiledTemplate = Template7.compile(content);
        var url = WEB_ROOT + "/cms/Article_getCircle";
        var data = "";
        data += 'bizId='+loginUser.id;
        data += '&columnId=b724159f5d380618b49f810083eee3c4';
        console.log(data);
        $$.getJSON(url, data, function(data){

            console.log(data);

            var result = data;

            // console.log(compiledTemplate(result));

            next(compiledTemplate(result));
        });
    },
    onPageInit:function (app, page) {

        $$('.btn-circle-list-delete').on('click', function(e){
            fm.f7_confirm(myApp, '是否确定删除？',function () {
                var articleId = e.target.dataset.id;
                console.log("delete " + articleId);

                var url = WEB_ROOT + "/cms/Article_deleteCircle";
                var data = 'articleId='+articleId;
                $$.getJSON(url, data, function(data){

                    console.log(data);

                    var result = data;

                    // console.log(compiledTemplate(result));

                    viewCircle.router.loadPage('circle/circle_list.jsp?r='+Math.random());
                    viewCircle.router.refreshPage();
                });

            });
        });


        $$('.btn-circle-list-share').on('click', function (e) {

            var articleId = e.target.dataset.id;
            var userId = e.target.dataset.userId;
            var title = e.target.dataset.title;
            var summaryText = e.target.dataset.summaryText;
            var avatar = e.target.dataset.avatar;

            var url = "/modern2/share/share_article.jsp?userId="+userId+"&articleId="+articleId+"&title="+title;
            url = fm.urlEncode(url);

            console.log(e);

            myApp.actions([
                // First buttons group
                [
                    // Group Label
                    {
                        text: '请选择',
                        label: true
                    },
                    // First button
                    {
                        text: '分享到微信朋友圈',
                        onClick: function () {
                            // share2Weixin();
                            window.android.invokeAndroid('{jsId:"weixin_share_timeline",articleId:"'+articleId+'",userId:"'+userId+'",title:"'+title+'",summaryText:"'+summaryText+'",url:"'+url+'",avatar:"'+avatar+'"}');
                        }
                    },
                    {
                        text: '分享到微信好友',
                        onClick: function () {
                            // share2Weixin();
                            window.android.invokeAndroid('{jsId:"weixin_share_session",articleId:"'+articleId+'",userId:"'+userId+'",title:"'+title+'",summaryText:"'+summaryText+'",url:"'+url+'",avatar:"'+avatar+'"}');
                        }
                    }
                ],
                // Second group
                [
                    {
                        text: '取消'
                    }
                ]
            ]);
        });
    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});

pagePool.put('circle-save', {
    id:'circle-save',
    name:'circle_save',
    url:'circle_save.jsp',
    preprocess:function(content, url, next){
        // alert('init:' + this.id);
        next(content);
    },
    onPageInit:function (app, page) {
        $$('#txt-circle-save-content').change();

        $$('#btn-circle-save').on('click',function () {
            var url = WEB_ROOT + "/cms/Article_circleSave";
            var data = $('#circle-save-form').serialize();


            data += '&bizId='+loginUser.id;
            data += '&columnId=b724159f5d380618b49f810083eee3c4';

            // console.log(data);
            $$.post(url, data, function (data) {
                viewCircle.router.loadPage('circle/circle_list.jsp?r='+Math.random());
                viewCircle.router.refreshPage();
            },function () {
                fm.f7_alert(myApp, '保存失败');
            });
        });
    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});

pagePool.put('circle-tag-list', {
    id:'circle-tag-list',
    name:'circle_tag_list',
    url:'circle_tag_list.jsp',
    preprocess:function(content, url, next){
        // alert('init:' + this.id);
        next(content);
    },
    onPageInit:function (app, page) {
        $$('.circle-tag-add').on('click', function () {
            myApp.prompt('新增的标签名', function (data) {
                // @data contains input value
                myApp.confirm('Are you sure that your name is ' + data + '?', function () {
                    myApp.alert('Ok, your name is ' + data + ' ;)');
                });
            });
        });
    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});

pagePool.put('production-list', {
    id:'production-list',
    name:'production_list',
    url:'production_list.jsp',
    preprocess:function(content, url, next){
        var compiledTemplate = Template7.compile(content);
        var url = WEB_ROOT + "/modern/s/production/Production_listProductionVO4modernGroupByIncomeType.action";
        var data = {};
        $$.getJSON(url, data, function(data){

            var result = data['returnValue'];

            // console.log(compiledTemplate(result));

            next(compiledTemplate(result));
        });
    },
    onPageInit:function (app, page) {

    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});

pagePool.put('production-detail', {
    id:'production-detail',
    name:'production_detail',
    url:'production_detail.jsp',
    preprocess:function(content, url, next){
        var compiledTemplate = Template7.compile(content);
        var parameters = getParameters(url);
        url = WEB_ROOT + "/modern/s/production/Production_getProductionWVOById.action?" + parameters;
        console.log(url);
        var data = {};
        $$.getJSON(url, data, function (data) {

            var result = data['returnValue'];

            console.log(result);

            next(compiledTemplate(result));
        });
    },
    onPageInit:function (app, page) {




        $$('#btn-production-share').on('click', function (e) {

            var productionId = e.target.dataset.id;
            var userId = e.target.dataset.userId;
            var title = e.target.dataset.title;
            var summaryText = e.target.dataset.summaryText;

            var url = "/modern2/share/share_production.jsp?productionId="+productionId+"&userId="+userId;
            url = fm.urlEncode(url);

            myApp.actions([
                // First buttons group
                [
                    // Group Label
                    {
                        text: '请选择',
                        label: true
                    },
                    {
                        text: '分享到微信朋友圈',
                        onClick: function () {
                            // share2Weixin();
                            window.android.invokeAndroid('{jsId:"weixin_share_timeline",userId:"'+userId+'",title:"'+title+'",summaryText:"'+summaryText+'",url:"'+url+'"}');
                        }
                    },
                    {
                        text: '分享到微信好友',
                        onClick: function () {
                            // share2Weixin();
                            window.android.invokeAndroid('{jsId:"weixin_share_session",userId:"'+userId+'",title:"'+title+'",summaryText:"'+summaryText+'",url:"'+url+'"}');
                        }
                    }
                ],
                // Second group
                [
                    {
                        text: '取消'
                    }
                ]
            ]);
        });
    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});



pagePool.put('customer-list', {
    id:'customer-list',
    name:'customer_list',
    url:'customer_list.jsp',
    preprocess:function(content, url, next){
        var compiledTemplate = Template7.compile(content);
        // var parameters = getParameters(url);
        url = WEB_ROOT + "/customer/CustomerPersonal_listCustomerPersonalVO?userId=" + loginUser['id'];
        console.log(url);
        var data = {};
        $$.getJSON(url, data, function(data){

            var result = data;

            next(compiledTemplate(result));
        });
    },
    onPageInit:function (app, page) {

    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});

pagePool.put('mine-list', {
    id:'mine-list',
    name:'mine_list',
    url:'mine_list.jsp',
    preprocess:function(content, url, next){
        var compiledTemplate = Template7.compile(content);
        // var parameters = getParameters(url);
        url = WEB_ROOT + "/system/Files_loadByModuleBizId.action?moduleId=9D451710&bizId=" + loginUser['id'];
        console.log(url);
        var data = {};

        var result = {};

        $$.getJSON(url, data, function(data){

            result['files'] = data['returnValue'];

            url = WEB_ROOT + "/system/User_load?user.id="+loginUser['id']+"&r=" + Math.random();
            data = {};
            $$.getJSON(url, data, function(data){
                // console.log(url);
                // console.log(data);
                result['info'] = data['returnValue'];

                console.log(result);

                next(compiledTemplate(result));
            },function(){
                fm.f7_alert(myApp, '加载失败');
            });
        });

    },
    onPageInit:function (app, page) {
        $$('.btn-logout').on('click', function(){
            logout();
        })

        $$('.btn-user-avatar').on('click',function(){
            myApp.confirm('是否更换头像？', function () {

                var d = {};
                d['jsId'] = "mine_change_avatar";

                // var stringify = JSON.stringify(d);
                // fm.convert2Json()
                //
                // console.log( typeof stringify);
                // console.log(stringify);

                window.android.invokeAndroid('{jsId:"mine_change_avatar"}');

                viewMine.router.refreshPage();

            });
        });
    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});


pagePool.put('mine-change-description', {
    id:'mine-change-description',
    name:'mine_change_description',
    url:'mine_change_description.jsp',
    preprocess:function(content, url, next){

        // console.log(content);
        // alert('init:' + this.id);

        var compiledTemplate = Template7.compile(content);
        var url = WEB_ROOT + "/system/User_load?user.id="+loginUser['id']+"&r=" + Math.random();
        var data = {};
        $$.getJSON(url, data, function(data){
            // console.log(url);
            // console.log(data);
            var result = data;

            next(compiledTemplate(result));
        },function(){
            fm.f7_alert(myApp, '加载失败');
        });
    },
    onPageInit:function (app, page) {

        $$('#mine-change-description-description').change();

        fm.f7_bind('#btn-mine-change-description', function () {
            // fm.f7_alert(myApp, '保存介绍2');

            var data = $('#form-mine-change-description').serialize();
            var url = WEB_ROOT + "/system/User_updateUserInfo";
            // console.log(data);

            $$.post(url, data, function(data, status, xhr){
                viewMine.router.loadPage('mine/more_list.jsp?r='+Math.random());
                viewMine.router.refreshPage();

            },function(xhr, status){
                fm.f7_alert(myApp, '更新失败');
            });

            // fm.f7_post(url, data, function(data){
            //     console.log(data);
            // },function(){
            //     fm.f7_alert(myApp, '更新失败');
            // });
        });
    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});


pagePool.put('customer-save', {
    id:'customer-save',
    name:'customer_save',
    url:'customer_save.jsp',
    preprocess:function(content, url, next){
        var compiledTemplate = Template7.compile(content);
        next(content);
        var parameters = getParameters(url);

        if (parameters != '') {
            url = WEB_ROOT + "/modern/s/customer/CustomerPersonal_loadCustomerPersonalVOById?"+parameters;
            // console.log(url);
            var data = {};
            $$.getJSON(url, data, function(data){

                var formData = data['returnValue'];


                // console.log(typeof(formData));

                //alert(JSON.stringify(formData));

                console.log(formData);

                // console.log(compiledTemplate(result));
                myApp.formFromJSON('#customer-save-form', formData);


            });
        }
    },
    onPageInit:function (app, page) {
        $$('#btn-customer-save').on('click', function(){

            // alert($('#customer-save-form').length);
            // return;

            var formData = myApp.formToJSON('#customer-save-form');
            // alert(JSON.stringify(formData));
            var data = {
                'personal.id':formData['id'],
                'personal.name':formData['name'],
                'personal.mobile':formData['mobile'],
                'personal.sex':formData['sex'],
                'personal.remark':formData['remark'],
                'personal.birthday':formData['birthday']
            };

            var url = WEB_ROOT + '/modern/s/customer/CustomerPersonal_registerModernByUser.action?';

            if (!fm.checkIsTextEmpty(formData['id'])) {
                // updateModern
                url = WEB_ROOT + '/modern/s/customer/CustomerPersonal_updateModern.action?';
            }


            // console.log(url);
            console.log('form data:');
            console.log(formData);

            fm.f7_post(myApp, url, data, function(data){
                myApp.alert('保存成功');
                viewCustomer.router.loadPage('customer/customer_list.jsp?r='+Math.random());
                viewCustomer.router.refreshPreviousPage();
            },function(){
                myApp.alert('保存失败');
            });


        });

        $$('#btn-customer-delete').on('click',function(){
            var formData = myApp.formToJSON('#customer-save-form');
            // alert(JSON.stringify(formData));
            var data = {
                'personal.id':formData['id'],
                'personal.name':formData['name'],
                'personal.mobile':formData['mobile'],
                'personal.sex':formData['gender']=='男'?1:2,
                'personal.birthday':formData['birthday']
            };

            if (!fm.checkIsTextEmpty(formData['id'])) {
                var url = WEB_ROOT + '/modern/s/customer/CustomerPersonal_deleteModern.action?';

                fm.f7_confirm(myApp, '是否确认删除客户？', function () {
                    fm.f7_post(myApp, url, data, function(data){
                        myApp.alert('操作成功');
                        viewCustomer.router.loadPage('customer/customer_list.jsp?r='+Math.random());
                        viewCustomer.router.refreshPreviousPage();
                    },function(){
                        myApp.alert('保存失败');
                    });
                })


            }
        });
    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});


var homeListPage = {
    id:'home-list',
    name:'home_list',
    url:'home_list.jsp',
    preprocess:function(content, url, next){
        var compiledTemplate = Template7.compile(content);
        var parameters = getParameters(url);
        url = WEB_ROOT + "/modern/s/cms/Article_getArticleList?" + parameters;
        console.log(url);
        var data = {};
        $$.getJSON(url, data, function (data) {

            var result = data['returnValue'];

            next(compiledTemplate(result));
        });
    },
    onPageInit:function (app, page) {
        $$('.btn-home-share').on('click', function (e) {

            var articleId = e.target.dataset.id;
            var userId = e.target.dataset.userId;
            var title = e.target.dataset.title;
            var summaryText = e.target.dataset.summaryText;

            var url = "/modern2/share/share_article.jsp?userId="+userId+"&articleId="+articleId+"&title="+title;
            url = fm.urlEncode(url);

            console.log(e);

            myApp.actions([
                // First buttons group
                [
                    // Group Label
                    {
                        text: '请选择',
                        label: true
                    },
                    // First button
                    {
                        text: '分享到微信朋友圈',
                        onClick: function () {
                            // share2Weixin();

                            window.android.invokeAndroid('{jsId:"weixin_share_timeline",articleId:"'+articleId+'",userId:"'+userId+'",title:"'+title+'",summaryText:"'+summaryText+'",url:"'+url+'"}');
                            // /modern2/share/share_article.jsp?userId="+userId+"&articleId="+articleId+"&title="+title;
                        }
                    },
                    {
                        text: '分享到微信好友',
                        onClick: function () {
                            // share2Weixin();
                            window.android.invokeAndroid('{jsId:"weixin_share_session",articleId:"'+articleId+'",userId:"'+userId+'",title:"'+title+'",summaryText:"'+summaryText+'",url:"'+url+'"}');
                        }
                    }
                ],
                // Second group
                [
                    {
                        text: '取消'
                    }
                ]
            ]);
        });

        $$('.btn-home-share-preview').on('click', function (e) {
            // console.log(e);
            var articleId = e.target.dataset.id;
            // console.log(articleId);
            // window.location = 'share/share_article.jsp?articleId='+articleId;
            viewHome.router.loadPage('share/share_article.jsp?articleId='+articleId);
        });
    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
};
pagePool.put('home-list', homeListPage);

