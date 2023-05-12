import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UploadFile } from '../models/uploadFile';
import { lastValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UploadFileService {

  constructor(private httpClient:HttpClient) { }

  upload(uploadFile:UploadFile):Promise<any> {
    const formData = new FormData()
    formData.set("name",uploadFile['name'])
    formData.set("title",uploadFile['title'])
    formData.set("comments",uploadFile['comments'])
    formData.set("arhive",uploadFile['archive'])
    return lastValueFrom(this.httpClient.post<UploadFile>('/upload',formData))
  }
}
