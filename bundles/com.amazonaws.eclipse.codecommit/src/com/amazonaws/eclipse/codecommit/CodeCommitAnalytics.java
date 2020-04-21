/*
 * Copyright 2011-2017 Amazon Technologies, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *    http://aws.amazon.com/apache2.0
 *
 * This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and
 * limitations under the License.
 */
package com.amazonaws.eclipse.codecommit;

import com.amazonaws.eclipse.core.AwsToolkitCore;
import com.amazonaws.eclipse.core.mobileanalytics.ToolkitAnalyticsManager;
import com.amazonaws.eclipse.core.mobileanalytics.ToolkitEvent.ToolkitEventBuilder;

public class CodeCommitAnalytics {

    private static final ToolkitAnalyticsManager ANALYTICS = AwsToolkitCore.getDefault().getAnalyticsManager();

    // Create Repository Event
    private static final String EVENT_CREATE_REPOSITORY = "CodeCommit-CreateRepository";
    // Clone Repository Event
    private static final String EVENT_CLONE_REPOSITORY = "CodeCommit-CloneRepository";
    // Delete Repository Event
    private static final String EVENT_DELETE_REPOSITORY = "CodeCommit-DeleteRepository";
    // Open Repository Editor
    private static final String EVENT_OPEN_REPOSITORY_EDITOR = "CodeCommit-OpenRepositoryEditor";
    // Repository Type
    private static final String EVENT_REPOSITORY_TYPE = "CodeCommit-RepositoryType";

    // Attribute
    private static final String ATTR_NAME_END_RESULT = "result";
    private static final String ATTR_NAME_REPOSITORY_IS_MAVEN = "Maven";

    public static void trackCreateRepository(EventResult result) {
        publishEventWithAttributes(EVENT_CREATE_REPOSITORY, ATTR_NAME_END_RESULT, result.getResultText());
    }

    public static void trackCloneRepository(EventResult result) {
        publishEventWithAttributes(EVENT_CLONE_REPOSITORY, ATTR_NAME_END_RESULT, result.getResultText());
    }

    public static void trackDeleteRepository(EventResult result) {
        publishEventWithAttributes(EVENT_DELETE_REPOSITORY, ATTR_NAME_END_RESULT, result.getResultText());
    }

    public static void trackOpenRepositoryEditor(EventResult result) {
        publishEventWithAttributes(EVENT_OPEN_REPOSITORY_EDITOR, ATTR_NAME_END_RESULT, result.getResultText());
    }

    public static void trackRepositoryIsMaven(boolean isMavenProject) {
        ANALYTICS.publishEvent(ANALYTICS.eventBuilder()
                .setEventType(EVENT_REPOSITORY_TYPE)
                .addBooleanMetric(ATTR_NAME_REPOSITORY_IS_MAVEN, isMavenProject)
                .build());
    }

    private static void publishEventWithAttributes(String eventType, String... attributes) {
        ToolkitEventBuilder builder = ANALYTICS.eventBuilder().setEventType(eventType);
        for (int i = 0; i < attributes.length; i += 2) {
            builder.addAttribute(attributes[i], attributes[i + 1]);
        }
        ANALYTICS.publishEvent(builder.build());
    }

    public static enum EventResult {

        SUCCEEDED("Succeeded"),
        FAILED("Failed"),
        CANCELED("Canceled")
        ;

        private final String resultText;

        private EventResult(String resultText) {
            this.resultText = resultText;
        }

        public String getResultText() {
            return resultText;
        }
    }

}
