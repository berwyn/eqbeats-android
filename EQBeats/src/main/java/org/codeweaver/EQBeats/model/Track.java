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
 * Created by Berwyn Codeweaver on 23/06/13.
 */
public class Track extends Model {

    @DatabaseField
    private String title;
    @DatabaseField(foreignAutoRefresh = true, foreign = true)
    private User   artist;
    @DatabaseField(foreignAutoRefresh = true, foreign = true)
    private Media  download;
    @DatabaseField(foreignAutoRefresh = true, foreign = true)
    private Media  stream;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getArtist() {
        return artist;
    }

    public void setArtist(User artist) {
        this.artist = artist;
    }

    public Media getDownload() {
        return download;
    }

    public void setDownload(Media download) {
        this.download = download;
    }

    public Media getStream() {
        return stream;
    }

    public void setStream(Media stream) {
        this.stream = stream;
    }

}
