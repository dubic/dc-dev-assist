/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
angular.module('app', ['ngMaterial', 'ui.router', 'ngMessages'])
        .controller('ModuleCtrl', ModuleCtrl)
        .controller('ViewFileCtrl', ViewFileCtrl)
        .config(function ($stateProvider, $urlRouterProvider) {
            $stateProvider
                    .state('dash', {
                        url: "/dash",
                        templateUrl: "dash.html"
                    })
                    .state('minipayment', {
                        url: "/minipayment",
                        templateUrl: "minipayment.html",
                        controller: 'ModuleCtrl'
                    })
                    .state('view', {
                        url: "/view?file",
                        templateUrl: "view-file.html",
                        controller: 'ViewFileCtrl',
                        resolve: {
                            FileMap: function ($http, $stateParams) {
                                return $http.post('/module/file/view', {path: $stateParams.file});
                            }
                        }
                    });
        })
        .run(function ($state) {
            $state.go('dash');
        })
        .factory('filesCache', ['$cacheFactory', function ($cacheFactory) {
                return $cacheFactory('files-cache');
            }])
        .directive('prettyprint', function () {
            return {
                'restrict': 'C',
                'link': function postLink(scope, element, attrs) {
                    //extracts language of the block
                    var langExtension = attrs['class'].match(/\blang(?:uage)?-([\w.]+)(?!\S)/);
                    if (langExtension)
                        langExtension = langExtension[1];
                    //inserts into element output of prettyPrintOne
//                    element.html(prettyPrintOne(element.html(), langExtension, true));
                }
            };
        });

