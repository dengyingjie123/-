var myModule = angular.module("myModule",[]);
myModule.controller('Test',function($scope){
    $scope.users = [{id:1,name:'u1'},{id:2,name:'u2'}]
});