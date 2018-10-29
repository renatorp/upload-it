import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes} from '@angular/router';
import { FileUploadComponent } from './file-upload/file-upload.component';
import { FilesGridComponent } from './files-grid/files-grid.component';
import { UserGridComponent } from './user-grid/user-grid.component';
import { LoginComponent } from './login/login.component';

const routes: Routes = [
 {  path: 'upload', component: FileUploadComponent},
 {  path: 'files-list', component: FilesGridComponent},
 {  path: 'users-list', component: UserGridComponent},
 {  path: 'login', component: LoginComponent}
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot(routes)
  ],
  exports: [ RouterModule ],
  declarations: []
})
export class AppRoutingModule { }
