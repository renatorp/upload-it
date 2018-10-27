import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { DropzoneModule } from 'ngx-dropzone-wrapper';
import { DROPZONE_CONFIG } from 'ngx-dropzone-wrapper';
import { DropzoneConfigInterface } from 'ngx-dropzone-wrapper';
import { AppComponent } from './app.component';
import { FileUploadComponent } from './file-upload/file-upload.component';
import { FilesGridComponent } from './files-grid/files-grid.component';
import { environment } from '../environments/environment';

const DEFAULT_DROPZONE_CONFIG: DropzoneConfigInterface = {
   url: environment.uploadFileUrl,
   method: 'post',
   maxFilesize: null,
   chunking: true,
   acceptedFiles: null,
   chunkSize: 1000000
 };

@NgModule({
  declarations: [
    AppComponent,
    FileUploadComponent,
    FilesGridComponent
  ],
  imports: [
    BrowserModule,
    DropzoneModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [
    { provide: DROPZONE_CONFIG, useValue: DEFAULT_DROPZONE_CONFIG}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
