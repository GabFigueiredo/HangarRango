import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  images: {
    remotePatterns: [
      new URL("https://placehold.co/300x200/cccccc/969696.png?font=lato"),
    ],
  },
};

export default nextConfig;
