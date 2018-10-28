import { Component, OnInit } from '@angular/core';
import { environment } from '../../environments/environment';
import { FileService } from '../service/file.service';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.css']
})
export class FileUploadComponent implements OnInit {

  userId: any;
  public config: { url: string; };

  constructor(private fileService: FileService) {
    // Remover userid fixo
    this.userId = localStorage.getItem('userId');
  }

  ngOnInit() {
    this.config = {
      url: environment.uploadFileUrl.replace('{0}', this.userId),
    };
  }

  public onUploadError(e) {
    this.fileService.reportUploadFailure(e[0].name, this.userId).subscribe(
      response => {},
      error => {}
    );
    console.log('Error: ' + e);
  }

  public onUploadSuccess(e) {
    this.fileService.reportUploadSuccess(e[0].name, this.userId).subscribe(
      response => {},
      error => {}
    );
    console.log('Succerss: ' + e);
  }

}
