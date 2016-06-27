/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function ModuleCtrl($scope, $http) {
    console.log('controller works!');
    $scope.config = {
        pegasus: '/media/sf_dublux/eclipse_workspace/pegasus-bundle',
        modulePath: '/media/sf_dublux/eclipse_workspace/module/minipayment',
        tfw: false,
        preparing: false,
        files: []
    };

    $scope.prepare = function () {
        $scope.config.preparing = true;
        $http.post('/module/' + $scope.config.moduleName + '/prepare', $scope.config).success(function (files) {
            $scope.config.preparing = false;

                $scope.config.files = files;
            for (var i = 0; i < $scope.config.files.length; i++) {
                if ($scope.config.files[i].found) {
                    $scope.config.files[i].status = 'found';
                    $scope.config.files[i].color = 'green';
                } else {
                    $scope.config.files[i].status = 'not found';
                    $scope.config.files[i].color = 'red';
                }

            }

        });
    };
}

