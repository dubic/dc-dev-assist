/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function ModuleCtrl($scope, $http, $mdDialog,filesCache) {
    console.log(filesCache.get('config'));
    
    
    $scope.config = filesCache.get('config') || {
        pegasus: '/media/sf_dublux/eclipse_workspace/pegasus-bundle',
        modulePath: '/media/sf_dublux/eclipse_workspace/module/minipayment',
        tfw: false,
        preparing: false,
        files: [],
        foundFiles: 0,
        notFoundFiles: 0,
        pristine: true
    };
    

    $scope.prepare = function () {
        $scope.config.preparing = true;
        $http.post('/module/' + $scope.config.moduleName + '/prepare', $scope.config).success(function (files) {
            $scope.config.preparing = false;
            $scope.config.pristine = false;

            $scope.config.files = files;
            for (var i = 0; i < $scope.config.files.length; i++) {
                if ($scope.config.files[i].found) {
                    $scope.config.files[i].status = 'found';
                    $scope.config.files[i].color = 'green';
                    $scope.config.foundFiles++;
                } else {
                    $scope.config.files[i].status = 'not found';
                    $scope.config.files[i].color = 'red';
                    $scope.config.notFoundFiles++;
                }
            }
            filesCache.put('config',$scope.config);
        });
    };

   

    $scope.processAll = function (ev) {
        var confirm = $mdDialog.confirm()
                .title('Would you like to continue?')
                .textContent('This will make all the changes required and saved to file.')
                .ariaLabel('Lucky day')
                .targetEvent(ev)
                .ok('Continue')
                .cancel('Cancel');
        $mdDialog.show(confirm).then(function () {
            runChanges();
        }, function () {

        });
    };

    function runChanges() {

    }
}

function ViewFileCtrl($scope, $http, FileMap, $timeout) {
//    console.log(FileMap);
    $scope.file = FileMap.data;
    $timeout(function () {

        prettyPrint();
    }, 500);

}

