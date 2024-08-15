import { api } from "@/lib/api-client";
import { MutationConfig } from "@/lib/react-query";
import { useMutation } from "@tanstack/react-query";
import { z } from "zod";
import { JwtResponse } from "../models/jwt-response";

// adjust based on backend
export const loginUserInputSchema = z.object({
  username: z.string().min(3).max(20),
  password: z.string().min(6),
});

export type LoginUserInput = z.infer<typeof loginUserInputSchema>;

export const loginUser = ({
  data,
}: {
  data: LoginUserInput;
}): Promise<JwtResponse> => {
  // adjust later
  return api.post("/users/login", data);
};

type UseLoginUserOptions = {
  mutationConfig?: MutationConfig<typeof loginUser>;
};

export const useLoginUser = ({ mutationConfig }: UseLoginUserOptions = {}) => {
  const { onSuccess, ...restConfig } = mutationConfig || {};

  return useMutation({
    ...restConfig,
    mutationFn: loginUser,
  });
};
