/*
 * ADOBE CONFIDENTIAL
 *
 * Copyright 2014 Adobe Systems Incorporated
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 */

"use strict";

/**
 * Image component JS Use-api script
 */
use(["image.js", "../utils/ResourceUtils.js"], function (imagePromise, ResourceUtils) {
    imagePromise.then(function(image) {
        if (image.linkUrl()) {
        	image.linkUrl = Packages.com.day.orion.web.helpers.URLResolver.resolveLocalizedHtmlLink(request, image.linkUrl());
        }


       /* ResourceUtils.getResource(image.fileReference() + "/jcr:content/metadata")
                .then(function (fileResourceContent) {
                     var width = fileResourceContent.properties["tiff:ImageWidth"];
                     if (image.width <= 0) {
        				image.width = width;
    				 }

                     var height = fileResourceContent.properties["tiff:ImageLength"];
                     if (image.height <= 0) {
        				image.height = height;
    				 }
                });*/
    })

    return imagePromise;

});
