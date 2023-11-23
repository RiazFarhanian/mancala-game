import { Injectable } from '@angular/core';
import axios, { AxiosInstance, AxiosRequestConfig, AxiosResponse, AxiosError } from 'axios';
import { SessionStorageService } from '../store/session.storage.service';



@Injectable({
  providedIn: 'root',
})
export class AxiosInterceptorService {

  private axiosInstance: AxiosInstance;

  constructor(private sessionStorageService: SessionStorageService) {
    this.axiosInstance = axios.create();

    // Set up request interceptor
    this.axiosInstance.interceptors.request.use(
      (config) => {
        // Modify request config (e.g., add headers)
        config.headers['Authorization'] = 'Bearer ' + this.sessionStorageService.get('token');
        return config;
      },
      (error: AxiosError) => {
        return Promise.reject(error);
      }
    );

// Set up response interceptor
// Add a response interceptor
    this.axiosInstance.interceptors.response.use(
        (response) => {
          // You can modify the response data here before it is returned
          return response;
        },
        (error: AxiosError) => {
          // Handle response error
          if (error.response) {
            // The request was made and the server responded with a status code
            // that falls out of the range of 2xx
            console.error('Response error:', error.response.status, error.response.data);
          } else if (error.request) {
            // The request was made but no response was received
            console.error('Request error:', error.request);
          } else {
            // Something happened in setting up the request that triggered an Error
            console.error('Error:', error.message);
          }

          // You can throw the error or return a custom response
          return Promise.reject(error);
        });

  }

    public get<T>(url: string, config?: AxiosRequestConfig): Promise<T> {
        return this.axiosInstance.get<T>(url, config).then(response => response.data);
      }

      public post<T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
        return this.axiosInstance.post<T>(url, data, config).then(response => response.data);
      }

      public put<T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
        return this.axiosInstance.put<T>(url, data, config).then(response => response.data);
      }

}
