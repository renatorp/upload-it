import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { NgModule, LOCALE_ID } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { DropzoneModule } from 'ngx-dropzone-wrapper';
import { DROPZONE_CONFIG } from 'ngx-dropzone-wrapper';
import { DropzoneConfigInterface } from 'ngx-dropzone-wrapper';
import { AppComponent } from './app.component';
import { FileUploadComponent } from './file-upload/file-upload.component';
import { FilesGridComponent } from './files-grid/files-grid.component';
import { DurationAsStringPipe } from './duration-as-string.pipe';
import { registerLocaleData } from '@angular/common';
import localePt from '@angular/common/locales/pt';

registerLocaleData(localePt, 'pt');

const DEFAULT_DROPZONE_CONFIG: DropzoneConfigInterface = {
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
    FilesGridComponent,
    DurationAsStringPipe
  ],
  imports: [
    BrowserModule,
    DropzoneModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [
    { provide: DROPZONE_CONFIG, useValue: DEFAULT_DROPZONE_CONFIG},
    { provide: LOCALE_ID, useValue: 'pt' }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
