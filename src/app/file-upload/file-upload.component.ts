import { Component, OnInit } from '@angular/core';
import { environment } from '../../environments/environment';
import { ApiService } from '../service/api.service';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.css']
})
export class FileUploadComponent implements OnInit {

  userId: any;
  public config: { url: string; };

  constructor(private apiService: ApiService) {
    // Remover userid fixo
    this.userId = 1;
  }

  ngOnInit() {
    this.config = {
      url: environment.uploadFileUrl.replace('{0}', this.userId),
    };
  }

  public onUploadError(e) {
    this.apiService.reportUploadFailure(e[0].name, this.userId).subscribe(
      response => {},
      error => {}
    );
    console.log('Error: ' + e);
  }

  public onUploadSuccess(e) {
    this.apiService.reportUploadSuccess(e[0].name, this.userId).subscribe(
      response => {},
      error => {}
    );
    console.log('Succerss: ' + e);
  }

}
