(function(){
    'use strict';

    var app = angular.module('pgds',[]);


    function PgdsCtrl($window, $http){

        var vm = this;
        var projectId = $window.projectId;
        var projectName = $window.projectName;
        var hrList = $window.hr;
        var taskList = $window.tasks;



        vm.costs = {};

        hrList.forEach(function(hr){
            vm.costs[hr.user_id] = hr.cost || 0;
        });

        vm.exportCosts = exportCosts;
        vm.updateHrCosts = updateHrCosts;

        function exportCosts(){

            var hrs = [];
            var tasks = [];

            taskList.forEach(function(task){

                var parentTask = assemblyTask(task);

                if(task.subtasks){
                    parentTask.subactivities = [];
                     task.subtasks.forEach(function(subtask){
                         parentTask.subactivities.push(assemblyTask(subtask))
                    });
                }

                tasks.push(parentTask)

                function assemblyTask(task){
                    return {
                        id : task.id,
                        hrs : task.hrs || [],
                        description : task.task_name,
                        dependencies : task.dependencies || [],
                        plannedStartDate : task.task_start_date.substr(0,10),
                        plannedEndDate : task.task_end_date.substr(0,10),
                        plannedDuration : task.task_duration,
                        startDate : task.realStartDate.substr(0,10),
                        endDate : task.realEndDate.substr(0,10),
                        duration : task.realDuration,
                        progress : task.task_percent_complete
                    };
                }
            })

            hrList.forEach(function(hr){
                hrs.push({
                    // id : hr.user_id,
                    name : hr.contact_full_name,
                    cost : vm.costs[hr.user_id]
                })
            });

            vm.exporting = true;
            $http.post('/dotproject/modules/pgds/ajax/generate_hr_template.php' , {
                project : projectName,
                hrs : hrs,
                activities : tasks
            }).then(function(){
                vm.exporting = false;
                alert("Data was successfully exported")
            });

        }

        function updateHrCosts(){
            $http.post('/dotproject/modules/pgds/ajax/update_hr_costs.php' , {
                project : projectId,
                costs : vm.costs
            });
        }

    }

    var deps = [
        '$window',
        '$http',
        PgdsCtrl
    ];

    app.controller('PgdsCtrl' , deps);

})()