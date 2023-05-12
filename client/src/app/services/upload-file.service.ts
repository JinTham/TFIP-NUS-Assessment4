import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ElementRef, Injectable } from '@angular/core';
import { firstValueFrom, lastValueFrom } from 'rxjs';
import { Bundle } from '../models/bundle';

@Injectable({
  providedIn: 'root'
})
export class UploadFileService {

  constructor(private httpClient:HttpClient) { }

  upload(form:any, zipFile:ElementRef) {
    const formData = new FormData()
    formData.set("name",form['name'])
    formData.set("title",form['title'])
    formData.set("comments",form['comments'])
    formData.set("archive",zipFile.nativeElement.files[0])
    console.log(formData.get('archive'))
    return lastValueFrom(this.httpClient.post('/upload', formData))
  }

  getBundle(bundleId:string):Promise<any> {
    return lastValueFrom(this.httpClient.get<Bundle>('/bundle/'+bundleId))
  }

  getAllBundles():Promise<any> {
    return lastValueFrom(this.httpClient.get<Bundle[]>('/bundles'))
  }
}
