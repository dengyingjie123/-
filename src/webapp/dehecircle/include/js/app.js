/*global angular */
'use strict';

/**
 * The main app module
 * @name app
 * @type {angular.Module}
 */
var app = angular.module('app', ['flow'])
    .config(['flowFactoryProvider', function (flowFactoryProvider) {

        flowFactoryProvider.defaults = {
            target: "/Upload_upload.action",
            //permanentErrors: [404, 500, 501],
            permanentErrors: [500, 501],
            maxChunkRetries: 1,
            chunkRetryInterval: 5000,
            simultaneousUploads: 4,
            singleFile: false,
            progressCallbacksInterval: 1,
            withCredentials: true,
            method: "octet",
            forceChunkSize: true,
            testChunks: false  //if true, doGet() will be called first then doPost()
        };

        flowFactoryProvider.on('catchAll', function (event) {
            console.log('catchAll', arguments);
        });
        // Can be used with different implementations of Flow.js
        // flowFactoryProvider.factory = fustyFlowFactory;
    }]);