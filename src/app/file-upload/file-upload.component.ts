import { Component, OnInit } from '@angular/core';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.css']
})
export class FileUploadComponent implements OnInit {

  userId: any;
  public config: { url: string; };

  constructor() {
    // Remover userid fixo
    this.userId = 1;
  }

  ngOnInit() {
    this.config = {
      url: environment.uploadFileUrl.replace('{0}', this.userId),
    };
  }

  public onUploadError(e) {
    console.log('Error');
  }

  public onUploadSuccess(e) {
    console.log('Succerss');
  }

}
