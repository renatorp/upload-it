export class FileUpload {


    constructor(id: string, userId: string, fileName: string, status: number,
        processDuration: number, quantityOfBlocks: number, download: string) {
            this.id = id;
            this.userId = userId;
            this.status = status;
            this.processDuration = processDuration;
            this.quantityOfBlocks = quantityOfBlocks;
            this.download = download;
    }

    id: string;
    userId: string;
    fileName: string;
    status: number;
    processDuration: number;
    quantityOfBlocks: number;
    download: string;
}
