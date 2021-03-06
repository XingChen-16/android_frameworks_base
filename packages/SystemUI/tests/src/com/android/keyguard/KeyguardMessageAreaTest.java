/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.android.keyguard;

import static junit.framework.Assert.assertEquals;

import static org.mockito.Mockito.mock;

import android.os.Handler;
import android.os.Looper;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.android.systemui.SysuiTestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@SmallTest
@RunWith(AndroidJUnit4.class)
public class KeyguardMessageAreaTest extends SysuiTestCase {
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private KeyguardMessageArea mMessageArea;

    @Before
    public void setUp() throws Exception {
        KeyguardUpdateMonitor monitor = mock(KeyguardUpdateMonitor.class);
        mHandler.post(()-> mMessageArea = new KeyguardMessageArea(mContext, null, monitor));
        waitForIdleSync();
    }

    @Test
    public void clearFollowedByMessage_keepsMessage() {
        mHandler.post(()-> {
            mMessageArea.setMessage("");
            mMessageArea.setMessage("test");
        });

        waitForIdleSync();

        CharSequence[] messageText = new CharSequence[1];
        mHandler.post(()-> {
            messageText[0] = mMessageArea.getText();
        });

        waitForIdleSync();

        assertEquals("test", messageText[0]);
    }

}
