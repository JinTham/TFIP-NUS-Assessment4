import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ElementRef, Injectable } from '@angular/core';
import { UploadFile } from '../models/uploadFile';
import { firstValueFrom, lastValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UploadFileService {

  constructor(private httpClient:HttpClient) { }

  upload(name:string, title:string, comments:string, zip:ElementRef) {
    const formData = new FormData()
    formData.set("name",name)
    formData.set("title",title)
    formData.set("comments",comments)
    formData.set("archive",zip.nativeElement.file[0])
    console.log(formData.get('archive'))
    const headers = new HttpHeaders().set("Content-Type","multipart/form-data")
    return firstValueFrom(this.httpClient.post('/upload', formData, {headers:headers}))
  }
}
