/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
angular.module('app', ['ngMaterial', 'ui.router','ngMessages'])
        .controller('ModuleCtrl',ModuleCtrl)
        .controller('ViewFileCtrl',ViewFileCtrl)
        .config(function ($stateProvider, $urlRouterProvider) {
            $stateProvider
                    .state('dash', {
                        url: "/dash",
                        templateUrl: "dash.html"
                    }).state('minipayment', {
                        url: "/minipayment",
                        templateUrl: "minipayment.html",
                        controller:'ModuleCtrl'
                    }).state('view', {
                        url: "/view",
                        templateUrl: "view-file.html",
                        controller: 'ViewFileCtrl'
                    });
        })
        .run(function ($state) {
            $state.go('dash');
        });

