import { SentenceModel } from "./sentence.model";

export class CardModel {
    id = 0;
    word = "";
    translation = "";
    plural = "";
    themaIds :number[] = [];
    sentenceIds : number[] = [];
    sentences : SentenceModel[] = [];
}
