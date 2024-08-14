import { api } from "@/lib/api-client";
import { MutationConfig } from "@/lib/react-query";
import { MessageResponse } from "@/types/api";
import { useMutation } from "@tanstack/react-query";
import { z } from "zod";

// adjust based on backend
export const loginUserInputSchema = z.object({
  email: z.string().email(),
  password: z.string().min(5),
});

export type LoginUserInput = z.infer<typeof loginUserInputSchema>;

export const loginUser = ({
  data,
}: {
  data: LoginUserInput;
}): Promise<MessageResponse> => {
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
