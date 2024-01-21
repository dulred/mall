
import axios from '@/request/axios'

export function getHome(){
    return axios.get("/index-infos");
}