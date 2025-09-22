export interface PixQrCodeResponse {
  data?: {
    id: string;
    amount: string;
    status: string;
    devMode: boolean;
    brCode: string;
    brCodeBase64: string;
    platformFee: string;
    createdAt: string;
    updatedAt: string;
    expiresAt: string;
    metadata: Metadata;
  };
  error: string;
}

export interface Metadata {
  externalId: string;
}
