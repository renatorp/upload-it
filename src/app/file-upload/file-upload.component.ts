import { Component, OnInit } from '@angular/core';
import { environment } from '../../environments/environment';
import { FileService } from '../service/file.service';
import { ErrorHandlerService } from '../error-handler.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.css']
})
export class FileUploadComponent implements OnInit {

  userId: any;
  public config: { url: string; };

  constructor(private fileService: FileService, private errorHandler: ErrorHandlerService, private toastrService: ToastrService) {
    this.userId = localStorage.getItem('userId');
  }

  ngOnInit() {
    this.config = {
      url: environment.uploadFileUrl.replace('{0}', this.userId),
    };
  }

  public onUploadError(e) {
    this.fileService.reportUploadFailure(e[0].name, this.userId).subscribe(
      response => { this.toastrService.error('Ocorreu uma falha no upload do arquivo!'); },
      error => { this.errorHandler.handleError(error); }
    );
    console.log('Error: ' + e);
  }

  public onUploadSuccess(e) {
    this.fileService.reportUploadSuccess(e[0].name, this.userId).subscribe(
      response => { this.toastrService.success('Upload concluído com sucesso!'); },
      error => { this.errorHandler.handleError(error); }
    );
    console.log('Succerss: ' + e);
  }

}
