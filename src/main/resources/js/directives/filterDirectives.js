recruitersDirectives.directive("showButton", ['$http', function($http) {
    return {
        link: function(scope, element, attrs) {
            element.on("click", function(event) {
//
            });
        }
    }
}]);

recruitersDirectives.directive('buttonShowAll', function() {
    return {
        restrict: "A",
        scope: {
            label: "@buttonShowAll"
        },
        replace: true,
        template: "<label class=\"btn btn-default\">{{label}}" +
           "<input type=\"checkbox\" name=\"showAll\" value=\"1\" ng-model=\"formData.showAll\" /></label>",
        link: function(scope, element, attrs) {
            scope.formData = scope.$parent.formData;
            element.find("input").css("display", "none");
            // TODO: Solve double click event registering with original element
            element.find("input").on("click", function() {
                element.toggleClass("active");
            });
            element.find("input").bind('change', function() {
                scope.$emit('formChange', null);
            });
        }
    };
});

recruitersDirectives.directive('buttonHide', function($rootScope) {
    return {
        restrict: "A",
        scope: {
            label: "@buttonHide",
            name: "@",
            initValue: "@"
        },
        replace: true,
        template: "<label class=\"btn btn-default\">{{label}}" +
            "<input type=\"checkbox\" name={{name}} value=\"1\" /></label>",
        compile: function(tElem, tAttrs) {
            tElem.find("input").attr("ng-model", "formData." + tAttrs.name);
            return function(scope, element, attrs) {
                scope.formData = scope.$parent.formData;
                scope.formData[scope.name] = scope.initValue;
                element.find("input").css("display", "none");
                // TODO: Solve double click event registering with original element
                element.find("input").on("click", function() {
                    element.toggleClass("active");
                });
                element.find("input").bind('change', function() {
                    scope.$emit('formChange', null);
                });
            }
        }
    };
});

recruitersDirectives.directive('watchChange', function() {
    return {
//        scope: {
//          onChange: "&watchChange"
//        },
        link: function(scope, element, attrs) {
            element.bind('change', function() {
                scope.$apply(attrs.watchChange);
            });
        }
    };
});