/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function ModuleCtrl($scope, $http, $mdDialog, filesCache, $q) {
    console.log(filesCache.get('config'));
    $scope.config = filesCache.get('config') || {
        pegasus: '/media/sf_dublux/eclipse_workspace/pegasus-bundle',
        modulePath: '/media/sf_dublux/eclipse_workspace/module/minipayment',
        tfw: false,
        preparing: false,
        files: [],
        foundFiles: 0,
        notFoundFiles: 0,
        processedFiles: 0,
        pristine: true
    };
    $scope.prepare = function () {
        $scope.config.preparing = true;
        $scope.config.foundFiles = 0;
        $scope.config.notFoundFiles = 0;
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
            filesCache.put('config', $scope.config);
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
        $scope.filesDone = true;
//        $scope.processing = true;
        $scope.config.processedFiles = 0;
//        for (var i = 0; i < $scope.config.files.length; i++) {
//            process($scope.config.files[i]);
//        }
//        $scope.processing = false;
        var indices = [];
        for (var i = 0; i < $scope.config.files.length; i++) {
            $scope.config.files[i].status = 'waiting';
            $scope.config.files[i].status = 'color';
            indices.push(i);
        }
        console.log('running all');
        process(indices);
    }

//    function process(file) {
//        file.status = 'processing';
//        file.color = 'blue';
//        $scope.filesDone = false;
//        $http.post('/module/' + $scope.config.moduleName + '/file/process?tfw=' + $scope.config.tfw, file)
//                .success(function (result) {
//                    file.status = result.status;
//                    if (result.status !== 'error') {
//                        file.color = 'green';
//                        $scope.config.processedFiles++;
//                    } else {
//                        file.color = 'red';
//                    }
//                    $scope.filesDone = true;
//                });
//    }

    function process(indices) {
        var index = indices.shift();
        if (angular.isUndefined(index))
            return;
        var file = $scope.config.files[index];
        
        file.status = 'processing';
        file.color = 'blue';
        var req = {
            method: 'POST',
            url: '/module/' + $scope.config.moduleName + '/file/process?tfw=' + $scope.config.tfw,
            data: file
        };

        $http(req).then(function (result) {
            file.status = result.data.status;
            if (result.data.status !== 'error') {
                file.color = 'green';
                $scope.config.processedFiles++;
            } else {
                file.color = 'red';
            }
            process(indices);
        }, function () {
            console.log('error occurred');
        });
    }
}

function ViewFileCtrl($scope, $http, FileMap, $timeout) {
//    console.log(FileMap);
    $scope.file = FileMap.data;
    $timeout(function () {

        prettyPrint();
    }, 500);
}

