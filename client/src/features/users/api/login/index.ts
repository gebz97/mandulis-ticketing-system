import { z } from "zod";

// adjust based on backend
export const loginFormSchema = z.object({
    email: z.string().email(),
    password: z.string().min(5),
  });
  
  export type LoginFormSchema = z.infer<typeof loginFormSchema>;