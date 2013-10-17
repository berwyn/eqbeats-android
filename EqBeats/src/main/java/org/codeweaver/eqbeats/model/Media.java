/*
 * Copyright (C) 2013 Berwyn Codeweaver
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.codeweaver.eqbeats.model;

import com.j256.ormlite.field.DatabaseField;

/**
* Created by Berwyn Codeweaver on 24/06/13.
*/
public class Media {

    @DatabaseField
    private String	art;
    @DatabaseField
    private String	opus;
    @DatabaseField
    private String	vorbis;
    @DatabaseField
    private String	aac;
    @DatabaseField
    private String	mp3;
    @DatabaseField
    private String	original;

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public String getOpus() {
        return opus;
    }

    public void setOpus(String opus) {
        this.opus = opus;
    }

    public String getVorbis() {
        return vorbis;
    }

    public void setVorbis(String vorbis) {
        this.vorbis = vorbis;
    }

    public String getAac() {
        return aac;
    }

    public void setAac(String aac) {
        this.aac = aac;
    }

    public String getMp3() {
        return mp3;
    }

    public void setMp3(String mp3) {
        this.mp3 = mp3;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }
}
