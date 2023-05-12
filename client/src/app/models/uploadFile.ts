import { ElementRef } from "@angular/core"

export interface UploadFile {
    name:string
    title:string
    comments:string
    archive:ElementRef
}